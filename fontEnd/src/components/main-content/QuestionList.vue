<template>
  <div id="container">
    <div id="question-list" v-loading.lock='isLoadingQuestion' element-loading-text="玩命加载中">
      <div id="choose-params-panel">
        请在这里选择问题排序
      </div>
      <div class="question-overview" v-for="questionOverview in questionOverviewList">
        <div class="publisherInfo">
          <img class="avatar" :src="questionOverview.publisherImgSrc">
          <span class=".publisher"> {{ questionOverview.publisherName }} </span>
        </div>
        <div class="summary">
          <h3>
            <router-link :to="{ path : '/questionDetail/' + questionOverview.id }">
              {{ questionOverview.title }}
            </router-link>
          </h3>
          <div class="types">
            {{ questionOverview.subject }} -> {{ questionOverview.course }}
          </div>
          <div class="publish-date">
            发布于 <span>{{ questionOverview.publishDateTime | moment("ddd, MMM Do YYYY") }} </span>
          </div>
        </div>
        <div class="info">
          <div class="answers">
            <el-tag type="gray">{{ questionOverview.answers }}回答</el-tag>
          </div>
          <div class="solved">
            <el-tag v-if="questionOverview.solved" type="green">已解决</el-tag>
            <el-tag v-else type="gray">未解决</el-tag>
          </div>
        </div>
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
      <div style="clear: both"></div>
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
  #question-list>div.question-overview{
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
  #question-list .question-overview .publisherInfo{
    float: left;
    margin-right: 10px;
    width: 75px;
    text-align: center;
    height: 100%;
    min-width: 50px;
  }
  #question-list .question-overview .publisherInfo .avatar{
    width: 50px;
    height: 50px;
    margin: 0 auto;
    vertical-align: middle;
    display: block;
    border: green solid 1px;
  }
  #question-list .question-overview .summary{
    padding: 5px;
    float: left;
    margin-right: 10px;
    width: 70%;
    min-height: 50px;
    border-right: solid grey 1px;
    border-left: solid grey 1px;
  }
  #question-list .question-overview .summary h3{
    overflow: hidden;
  }
  #question-list .question-overview .summary .publish-date{
    clear: left;
    float: right;
  }
  #question-list .question-overview .info{
    float: right;
    width: 15%;
  }
  #question-list .question-overview .info div{
    display: inline-block;
    margin-right: 10px;
    padding: 8px 5px;
    text-align: center;
    width: 50%;
    vertical-align: middle;
  }
  #question-list #pagination-panel{
    float: right;
  }
  .summary a {
    color: #38935f;
  }
</style>
<script>
  import { getAllQuestion } from '@/api/question'
  import { Message } from 'element-ui'
  export default {
    data () {
      return {
        currentPage: 0,
        pageSize: 5,
        total: 0,
        isLoadingQuestion: true,
        questionOverviewList: [],
        pageSizes: [5, 10]
      }
    },
    created () {
      this.updateQuestionType()
    },
    methods: {
      updateQuestionType () {
        this.resetData()
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
        getAllQuestion(this.currentPage, this.pageSize).then((response) => {
          if (response.status === '200') {
            this.questionOverviewList = response.result.questionOverviewList
            this.total = response.result.totalNumber
            this.currentPage = response.result.currentPage
          }
          this.isLoadingQuestion = false
        }).catch((e) => {
          Message({
            message: '无法获取问题，请稍后重试！',
            type: 'error',
            duration: 1000
          })
          this.isLoadingQuestion = false
        })
      },
      fetchQuestionByType (questionType) {
        alert(questionType + ' ' + this.currentPage + ' ' + this.pageSize)
      },
      resetData () {
        this.currentPage = 0
        this.pageSize = 5
        this.total = 0
        this.isLoadingQuestion = true
        this.questionOverviewList = []
      }
    },
    watch: {
      '$route' (to, from) {
        this.updateQuestionType()
      }
    }
  }
</script>
