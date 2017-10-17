<template>
  <div id="questionDetail">
    <div id="question-answer">
      <div id="questionContent" v-loading='questionLoading' element-loading-text="拼命加载中">
        <div id="question-top-panel">
          <div class="question-title">
            <h3>
              {{ question.questionTitle }}
            </h3>
          </div>
          <div class="question-info">
            <el-button :disabled="isSendingFollow" @click.prevent="unFollowQuestion()" v-if="hasFollow" size="small" type="success">取消关注</el-button>
            <el-button :disabled="isSendingFollow" @click.prevent="followQuestion()" v-else size="small" type="success">关注问题</el-button>
            <el-button @click.prevent="$refs.dialog.open()" size="small" icon="edit" type="info">写回答</el-button>
          </div>
        </div>
        <div id="question-detail" v-html="question.questionContent">
        </div>
      </div>
      <div id="answers-panel" v-loading="answerLoading" element-loading-text="拼命加载中">
        <div id="answer-summary">
          共有 {{ answerNum }} 个回答
          <span id="sort-panel">排序</span>
        </div>
        <div v-for="answer in answers" class="answer">
          <answer :answer="answer" @onToggleComment="lastScrollTop = -1"></answer>
        </div>
        <div style="text-align: center" v-if="loadingMore">
          <i class="el-icon-loading"></i> 玩命加载中，请稍后
        </div>
      </div>
    </div>
    <answer-dialog @submitAnswer="submit" ref="dialog"></answer-dialog>
  </div>
</template>
<style scoped>
  #questionContent,#answers-panel{
    border: 1px solid #ddd;
    background: #f9fafc;
    padding: 2rem;
    margin: 1rem;
  }

  #sort-panel{
    float: right;
    margin-right: 5px;
  }



  #question-detail{
    clear: both;
    margin-top: 70px;
  }

  #question-top-panel{
  }
  .question-title{
    float: left;
    width: 60%;
  }
  .question-info{
    float: right;
    width: 20%;
  }

  #answer-summary{
    margin-bottom: 10px;
    border-bottom: solid 2px saddlebrown;
    padding-bottom: 5px;
  }
  .answer{
    border-bottom: 1px solid grey;
    margin-bottom: 10px;
  }

</style>
<script>
  import Answer from '../answer/Answer.vue'
  import { getQuestion, postAnswer, getAnswerNum, getLimitAnswer, hasFollowQuestion, unFollowQuestion, followQuestion } from '@/api/question'
  import { Message } from 'element-ui'
  import '../../../static/UE/ueditor.parse'
  import AnswerInputDialog from '@/components/answer/AnswerInputDialog'
  import $ from 'jquery'
  export default {
    components: { 'answer-dialog': AnswerInputDialog, 'answer': Answer },
    data () {
      return {
        question: {
          questionTitle: '',
          questionContent: '',
          questionId: null
        },
        hasFollow: false,
        questionLoading: true,
        answerLoading: true,
        answerNum: -1,
        startIndex: 0,
        onceNum: 5,
        answers: [],
        userId: 1,
        questionId: null,
        loadingMore: false,
        lastScrollTop: 0,
        isSendingFollow: false
      }
    },
    mounted: function () {
      this.getQuestion()
      this.getAnswer()
      $(window).bind('scroll', this.scrollMethod)
    },
    methods: {
      scrollMethod () {
        if ($(document).scrollTop() < this.lastScrollTop) {
          return
        }
        let last = this.lastScrollTop
        this.lastScrollTop = $(document).scrollTop()
        if (last < 0) {
          return
        }
        if (!($(document).scrollTop() + $(window).height() > $(document).height() - 1)) {
          return
        }
        if (this.answerNum <= 0) {
          return
        }
        if (this.startIndex >= this.answerNum) {
          this.changeLoadingStatus()
          Message({
            message: '哥，这回真没有了！',
            type: 'info',
            duration: 1000
          })
          return
        }
        if (!this.loadingMore && this.canLoading()) {
          this.loadingMore = true
          this.getData()
        }
      },
      canLoading () {
        return this.answerNum > 0 && this.startIndex < this.answerNum
      },
      getData () {
        const questionId = this.$route.params.questionId
        getLimitAnswer(questionId, this.startIndex, this.onceNum)
          .then((response) => {
            let answers = response.result
            let length = answers.length
            for (var i = 0; i < length; i++) {
              let answer = answers[i]
              this.answers.push(answer)
            }
            this.startIndex += length
            this.answerLoading = false
            this.changeLoadingStatus()
          }).catch((e) => {
            Message({
              message: '获取答案失败，请稍后再试',
              type: 'error',
              duration: 5 * 1000
            })
          })
      },
      changeLoadingStatus () {
        if (this.loadingMore) {
          this.loadingMore = false
        }
      },
      getQuestion () {
        const questionId = this.$route.params.questionId
        let _this = this
        getQuestion(questionId).then((response) => {
          _this.question.questionTitle = response.result.questionTitle
          _this.question.questionContent = response.result.questionContent
          _this.question.questionId = response.result.id
          _this.getFollowStatus()
          _this.$nextTick(function () {
            window.uParse('#questionContent', {
              rootPath: '../../static/UE/'
            })
            _this.questionLoading = false
          })
        }).catch((e) => {
          Message({
            message: '获取问题失败！',
            type: 'error',
            duration: 5 * 1000
          })
        })
      },
      getAnswer () {
        const questionId = this.$route.params.questionId
        let _this = this
        getAnswerNum(questionId).then((response) => {
          _this.answers = []
          _this.answerNum = response.result
          _this.startIndex = 0
          _this.lastScrollTop = 0
          if (_this.answerNum === 0) {
            this.answerLoading = false
          } else {
            _this.getData()
          }
        }).catch((e) => {
          Message({
            message: '请求数据出错，请稍后再试！',
            type: 'error',
            duration: 5 * 1000
          })
        })
      },
      submit: function (answer) {
        postAnswer(answer, this.question.questionId, this.userId).then((response) => {
          Message({
            message: '感谢你的答案!',
            type: 'success',
            duration: 5 * 1000
          })
          this.getAnswer()
        }).catch((e) => {
          Message({
            message: '对不起，回答失败，请稍后再试！',
            type: 'error',
            duration: 5 * 1000
          })
        })
      },
      getFollowStatus () {
        const questionId = this.question.questionId
        hasFollowQuestion(questionId, this.userId).then((response) => {
          this.hasFollow = response.result
        }).catch((e) => {
          Message({
            message: '获取信息失败，请稍后重试！',
            type: 'error',
            duration: 1000
          })
        })
      },
      unFollowQuestion () {
        const questionId = this.question.questionId
        this.isSendingFollow = true
        unFollowQuestion(questionId, this.userId).then((response) => {
          if (response.result === true) {
            this.hasFollow = false
            Message({
              message: '取消关注成功！',
              type: 'success',
              duration: 1000
            })
          }
          this.isSendingFollow = false
        }).catch((e) => {
          Message({
            message: '取消关注失败，请稍后再试！',
            type: 'error',
            duration: 1000
          })
          this.isSendingFollow = false
        })
      },
      followQuestion () {
        const questionId = this.question.questionId
        this.isSendingFollow = true
        followQuestion(questionId, this.userId).then((response) => {
          if (response.result === true) {
            this.hasFollow = true
            Message({
              message: '关注问题成功！',
              type: 'success',
              duration: 1000
            })
          }
          this.isSendingFollow = false
        }).catch((e) => {
          Message({
            message: '关注问题失败，请稍后再试！',
            type: 'error',
            duration: 1000
          })
          this.isSendingFollow = false
        })
      }
    },
    beforeRouteLeave (to, from, next) {
      // 导航离开该组件的对应路由时调用
      // 可以访问组件实例 `this`
      $(window).unbind()
      next(true)
    }
  }
</script>
