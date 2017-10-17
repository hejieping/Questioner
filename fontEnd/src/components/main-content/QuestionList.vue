<template>
  <div id="container">
    <div id="question-list" v-loading.lock='isLoadingQuestion' element-loading-text="玩命加载中">
      <div v-if="isNoData">
        <el-alert :closable="false" title="对不起，找不到任何问题！" type="info" show-icon>
        </el-alert>
        <el-button @click.prevent="$router.push('/editQuestion')" style="margin-top: 20px; float: right" size="small" type="success" icon="edit">去提问</el-button>
      </div>
      <div v-else>
        <el-button size="small" type="info" v-if="hasFollow" icon="star-off">取消关注</el-button>
        <el-button v-else size="small" type="success" icon="star-on">关注</el-button>
      <span id="choose-params-panel">
        <el-radio-group v-model="sortParamRadio" @change="radioChange()">
          <el-radio-button label="最新问题"></el-radio-button>
          <el-radio-button label="最热问题"></el-radio-button>
        </el-radio-group>
      </span>
      <div style="clear: both"></div>
      <div class="question-overview" v-for="questionOverview in questionOverviewList">
        <question-overview :questionOverview="questionOverview"></question-overview>
      </div>
      <div id="pagination-panel">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage + 1"
          :page-sizes="pageSizes"
          :page-size='pageSize'
          layout="total, sizes, prev, pager, next, jumper"
          :total='total'>
        </el-pagination>
      </div>
      <div style="clear: both; margin-bottom: 20px"></div>
      </div>
    </div>
  </div>

</template>
<style scoped>
  #question-list{
    margin-left: 5%;
    margin-right: 5%;
    width: 90%;
    position: relative;
  }
  #question-list div.question-overview{
    border-bottom: 2px solid #c0ccda;
    padding: 12px 0 10px 0;
    background: #f9fafd;
    overflow: hidden;
    position: relative;
    margin-bottom: 5px;
  }

  #question-list .question-overview:hover{
    box-shadow: #666 0px 0px 10px;
  }

  #question-list #pagination-panel{
    float: right;
  }

  #choose-params-panel {
    float: right;
    margin-bottom: 10px;
  }

</style>
<script>
  import { getAllQuestion, getQuestionByType } from '@/api/question'
  import { Message } from 'element-ui'
  import bus from '../../assets/eventBus.js'
  import store from '@/store'
  import QuestionOverview from '../../components/question/QuestionOverview.vue'
  export default {
    components: { 'question-overview': QuestionOverview },
    beforeRouteEnter (to, from, next) {
      store.dispatch('enter_questionList_page')
      next(true)
    },
    data () {
      return {
        currentPage: 0,
        pageSize: 5,
        total: 0,
        isLoadingQuestion: true,
        questionOverviewList: [],
        pageSizes: [5, 10],
        searchKeyWord: '',
        sortPrams: '',
        sortParamRadio: '最新问题'
      }
    },
    mounted () {
      bus.$off('searchQuestion')
      bus.$on('searchQuestion', (keyWord) => {
        this.searchQuestion(keyWord)
      })
    },
    created () {
      if (this.$route.params.keyWord) {
        this.searchKeyWord = this.$route.params.keyWord
      }
      this.updateQuestion()
    },
    methods: {
      updateQuestion () {
        this.fetchQuestion()
        this.resetData()
      },
      radioChange () {
        this.sortPrams = this.sortParamRadio === '最新问题' ? 'id' : 'views'
        this.fetchQuestion()
      },
      fetchQuestion () {
        const questionType = this.$route.params.questionType
        this.isLoadingQuestion = true
        if (questionType) {
          this.fetchQuestionByType(questionType)
        } else {
          this.fetchAllQuestion()
        }
      },
      handleSizeChange (val) {
        this.pageSize = val
        this.fetchQuestion()
      },
      handleCurrentChange (val) {
        this.currentPage = val - 1
        this.fetchQuestion()
      },
      fetchAllQuestion () {
        getAllQuestion(this.searchKeyWord, this.currentPage, this.pageSize, this.sortPrams).then((response) => {
          this.handleResponse(response)
        }).catch((e) => {
          this.handleError(e)
        })
      },
      handleResponse (response) {
        if (response.status === '200') {
          this.questionOverviewList = response.result.questionOverviewList
          this.total = response.result.totalNumber
          this.currentPage = response.result.currentPage
        }
        this.isLoadingQuestion = false
      },
      handleError (e) {
        Message({
          message: '无法获取问题，请稍后重试！',
          type: 'error',
          duration: 1000
        })
        this.isLoadingQuestion = false
      },
      fetchQuestionByType (questionType) {
        getQuestionByType(this.searchKeyWord, questionType, this.currentPage, this.pageSize, this.sortPrams).then((response) => {
          this.handleResponse(response)
        }).catch((e) => {
          this.handleError(e)
        })
      },
      resetData () {
        this.currentPage = 0
        this.pageSize = 5
        this.total = 0
        this.isLoadingQuestion = true
        this.questionOverviewList = []
      },
      searchQuestion (keyWord) {
        this.searchKeyWord = keyWord
        this.updateQuestion()
      }
    },
    computed: {
      isNoData: function () {
        return !this.isLoadingQuestion && this.questionOverviewList.length === 0
      }
    },
    watch: {
      '$route' (to, from) {
        this.updateQuestion()
      }
    },
    beforeRouteLeave (to, from, next) {
      store.dispatch('leave_questionList_page')
      next(true)
    }
  }
</script>
