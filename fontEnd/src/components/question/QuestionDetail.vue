<template>
  <div id="questionDetail">
    <div id="question-answer">
      <question @onPublisherId="initQuestionPublisherId($event)" @getAnswer="getAnswer()"></question>
      <div id="answers-panel" v-loading.lock="answerLoading" element-loading-text="拼命加载中">
        <div id="answer-summary">
          共有 {{ answerNum }} 个回答
          <el-radio-group @change="answerSortChange()" size="small" id="sort-panel" v-model="sortParamLabel">
            <el-radio-button label="默认"></el-radio-button>
            <el-radio-button label="时间"></el-radio-button>
          </el-radio-group>
        </div>
        <div v-for="answer in answers" class="answer">
          <answer :isCurrentUser="isCurrentUser" :answer="answer" @onToggleComment="lastScrollTop = -1"></answer>
        </div>
        <div style="text-align: center" v-if="loadingMore">
          <i class="el-icon-loading"></i> 玩命加载中，请稍后
        </div>
      </div>
    </div>
  </div>
</template>
<style scoped>
  #answers-panel{
    border: 1px solid #ddd;
    background: #f9fafc;
    padding: 2rem;
    margin: 1rem;
  }

  #sort-panel{
    float: right;
    margin-right: 5px;
  }

  #answer-summary{
    margin-bottom: 10px;
    border-bottom: solid 2px saddlebrown;
    padding-bottom: 5px;
  }

</style>
<script>
  import Answer from '../answer/Answer.vue'
  import Question from '../question/QuestionComponent.vue'
  import { getAnswerNum, getLimitAnswer } from '@/api/question'
  import { Message } from 'element-ui'
  import $ from 'jquery'
  import { mapGetters } from 'vuex'

  export default {
    components: { 'answer': Answer, 'question': Question },
    data () {
      return {
        answerLoading: true,
        answerNum: -1,
        startIndex: 0,
        onceNum: 5,
        answers: [],
        questionId: '',
        publisherId: '',
        loadingMore: false,
        lastScrollTop: 0,
        isSendingFollow: false,
        sortParamLabel: '默认',
        sortParam: ''
      }
    },
    mounted: function () {
      this.questionId = this.$route.params.questionId
      this.getAnswer()
    },
    computed: {
      ...mapGetters(['user']),
      isCurrentUser: function () {
        return this.user !== null && this.publisherId === this.user.id
      }
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
        if (!this.answerLoading && !this.loadingMore && this.canLoading()) {
          this.loadingMore = true
          this.getData()
        }
      },
      canLoading () {
        return this.answerNum > 0 && this.startIndex < this.answerNum
      },
      getData () {
        getLimitAnswer(this.questionId, this.startIndex, this.onceNum, this.sortParam)
          .then((response) => {
            let answers = response.result
            let length = answers.length
            for (let i = 0; i < length; i++) {
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
      getAnswer () {
        $(window).unbind()
        this.answerLoading = true
        let _this = this
        getAnswerNum(_this.questionId).then((response) => {
          _this.answers = []
          _this.answerNum = response.result
          _this.startIndex = 0
          _this.lastScrollTop = 0
          if (_this.answerNum === 0) {
            this.answerLoading = false
          } else {
            _this.getData()
          }
          $(window).bind('scroll', this.scrollMethod)
        }).catch((e) => {
          Message({
            message: '请求数据出错，请稍后再试！',
            type: 'error',
            duration: 5 * 1000
          })
          $(window).bind('scroll', this.scrollMethod)
        })
      },
      answerSortChange () {
        this.sortParam = this.sortParamLabel === '默认' ? '' : 'id'
        this.getAnswer()
      },
      initQuestionPublisherId (userId) {
        this.publisherId = userId
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
