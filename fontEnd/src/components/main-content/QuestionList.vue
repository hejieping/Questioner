<template>
  <div>
    <div id="question-list">
      <div  v-loading='isLoadingQuestion' element-loading-text="玩命加载中">
      <div v-if="isNoData">
        <span v-if="canFollow">
          <el-button @click="unFollowQuestionType()"  :disabled="isSendingFollow" size="small" type="info" v-if="hasFollow" icon="star-off">取消关注</el-button>
          <el-button @click="followQuestionType()" :disabled="isSendingFollow" v-else size="small" type="success" icon="star-on">关注</el-button>
        </span>
        <el-alert style="margin-top: 10px" :closable="false" title="对不起，找不到任何问题！" type="info" show-icon>
        </el-alert>
        <el-button @click.prevent="$router.push('/editQuestion')" style="margin-top: 20px; float: right" size="small" type="success" icon="edit">去提问</el-button>
        <div style="clear: both"></div>
      </div>
      <div v-else>
        <span v-if="canFollow">
        <el-button @click="unFollowQuestionType()"  :disabled="isSendingFollow" size="small" type="info" v-if="hasFollow" icon="star-off">取消关注</el-button>
        <el-button @click="followQuestionType()" :disabled="isSendingFollow" v-else size="small" type="success" icon="star-on">关注</el-button>
        </span>
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
      <div v-if="showRecommendQuestion">
        <el-card  class="box-card">
          <div slot="header" class="clearfix">
            <span style="line-height: 20px;">你可能感兴趣</span>
            <el-button @click.prevent="loadingRecommendQuestion()" size="mini" icon="fa fa-refresh" style="float: right" type="primary">换一换</el-button>
          </div>
          <div class="recommend-question" v-loading="isLoadingRecommend" element-loading-text="玩命加载中">
            <div  class="question-overview" v-for="questionOverview in recommendQuestionOverviewList">
              <question-overview :questionOverview="questionOverview"></question-overview>
            </div>
          </div>
        </el-card>
      </div>
    </div>
  </div>

</template>
<style scoped>
  #question-list{
    margin-left: 5%;
    margin-right: 5%;
    width: 90%;
  }

  div.question-overview{
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
  import { getAllQuestion, getQuestionByType, getRecommendQuestion } from '@/api/question'
  import { Message } from 'element-ui'
  import { getFollowStatus, followQuestionType, unFollowQuestionType } from '@/api/questionType'
  import bus from '../../assets/eventBus.js'
  import store from '@/store'
  import { mapGetters } from 'vuex'
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
        userId: 1,
        sortPrams: '',
        sortParamRadio: '最新问题',
        hasFollow: false,
        isSendingFollow: true,
        isLoadingRecommend: false,
        recommendQuestionOverviewList: []
      }
    },
    mounted () {
      bus.$off('searchQuestion')
      bus.$on('searchQuestion', (keyWord) => {
        this.searchQuestion(keyWord)
      })
      let _this = this
      bus.$off('loginSuccess')
      bus.$on('loginSuccess', function () {
        _this.getFollowStatus()
        _this.loadingRecommendQuestion()
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
        this.getFollowStatus()
        this.loadingRecommendQuestion()
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
        this.hasFollow = false
        this.isSendingFollow = true
      },
      searchQuestion (keyWord) {
        this.searchKeyWord = keyWord
        this.updateQuestion()
      },
      getFollowStatus () {
        const questionType = this.$route.params.questionType
        if (!(questionType > 0)) {
          return
        }
        if (this.hasLogin === false) {
          this.isSendingFollow = false
          this.hasFollow = false
          return
        }
        getFollowStatus(questionType).then((response) => {
          this.isSendingFollow = false
          this.hasFollow = response.result
        }).catch((e) => {
          Message({
            message: '获取关注信息失败，请稍后重试！',
            type: 'error',
            duration: 1000
          })
        })
      },
      followQuestionType () {
        const questionType = this.$route.params.questionType
        if (!(questionType > 0)) {
          return
        }
        if (!this.hasLogin) {
          bus.$emit('requestLogin')
          return
        }
        this.isSendingFollow = true
        followQuestionType(questionType).then((response) => {
          if (response.status === '200') {
            if (response.result === true) {
              this.hasFollow = true
              Message({
                message: '关注成功！',
                type: 'success',
                duration: 1000
              })
            }
          }
          this.isSendingFollow = false
        }).catch((e) => {
          Message({
            message: '关注失败，请稍后重试！',
            type: 'error',
            duration: 1000
          })
          this.isSendingFollow = false
        })
      },
      unFollowQuestionType () {
        const questionType = this.$route.params.questionType
        if (!(questionType > 0)) {
          return
        }
        this.isSendingFollow = true
        unFollowQuestionType(questionType).then((response) => {
          if (response.status === '200') {
            if (response.result === true) {
              this.hasFollow = false
              Message({
                message: '取消关注成功！',
                type: 'success',
                duration: 1000
              })
            }
          }
          this.isSendingFollow = false
        }).catch((e) => {
          Message({
            message: '取消关注失败，请稍后重试！',
            type: 'error',
            duration: 1000
          })
          this.isSendingFollow = false
        })
      },
      loadingRecommendQuestion () {
        if (!this.hasLogin) {
          return
        }
        this.isLoadingRecommend = true
        getRecommendQuestion().then((response) => {
          if (response.status === '200') {
            this.recommendQuestionOverviewList = response.result
          }
          this.isLoadingRecommend = false
        }).catch((e) => {
          Message({
            message: '加载推荐问题失败，请稍后重试！',
            type: 'error',
            duration: 2000
          })
          this.isLoadingRecommend = false
        })
      }
    },
    computed: {
      isNoData: function () {
        return !this.isLoadingQuestion && this.questionOverviewList.length === 0
      },
      canFollow () {
        return this.$route.params.questionType >= 0
      },
      showRecommendQuestion () {
        return !(this.$route.params.questionType >= 0) && this.hasLogin
      },
      ...mapGetters(['hasLogin'])
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
