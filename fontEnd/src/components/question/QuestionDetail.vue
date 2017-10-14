<template>
  <div id="questionDetail">
    <div id="question-answer">
      <div id="questionContent" v-loading.lock='questionLoading'>
        <div id="question-top-panel">
          <div class="question-title">
            <h3>
              {{ question.questionTitle }}
            </h3>
          </div>
          <div class="question-info">
            <el-button size="small" type="success">关注问题</el-button>
            <el-button @click.prevent="$refs.dialog.open()" size="small" icon="edit" type="info">写回答</el-button>
          </div>
        </div>
        <div id="question-detail" v-html="question.questionContent">
        </div>
      </div>
      <div id="answers-panel" v-loading.lock="answerLoading">
        <div id="answer-summary">
          共有 100 个回答
          <span id="sort-panel">排序</span>
        </div>

        <div class="answer">
          <div class="user-info">
            <span><img class="user-avatar" /></span>
            <span>名字</span>
          </div>
          <div class="answer-info">
            回答信息
          </div>
          <div class="feedback">
            好评
          </div>
        </div>
        <div class="answer">
          <div class="user-info">
            <span><img class="user-avatar" /></span>
            <span>名字</span>
          </div>
          <div class="answer-info">
            回答信息
          </div>
          <div class="feedback">
            好评
          </div>
        </div>
        <div class="answer">
          <div class="user-info">
            <span><img src="" class="user-avatar" /></span>
            <span>名字</span>
          </div>
          <div class="answer-info">
            回答信息
          </div>
          <div class="feedback">
            好评
          </div>
        </div>

        <div v-for="answer in answers" class="answer">
          <div class="user-info">
            <span><img :src="answer.userAvatar"   class="user-avatar"/></span>
            <span>{{ answer.username }}</span>
          </div>
          <div class="answer-info" v-html="answer.answerContent">
          </div>
          <div class="feedfack">
            好评
          </div>
        </div>

      </div>
    </div>
    <answer-dialog ref="dialog"></answer-dialog>
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

  #answers-panel .answer{
    border-bottom: 1px solid grey;
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

  .answer .user-avatar{
    width: 38px;
    height: 38px;
    border-radius: 2px;
    background: #fff;
    vertical-align: top;
    margin-right: 10px;
    margin-bottom: 10px;
  }
</style>
<script>
  import { getQuestion } from '@/api/question'
  import { Message } from 'element-ui'
  import '../../../static/UE/ueditor.parse'
  import AnswerInputDialog from '@/components/answer/AnswerInputDialog'
  export default {
    components: { 'answer-dialog': AnswerInputDialog },
    data () {
      return {
        question: {
          questionTitle: '',
          questionContent: '',
          questionId: null
        },
        questionLoading: true,
        answerLoading: false,
        limit: 0,
        answers: []
      }
    },
    mounted: function () {
      this.getQuestion()
      window.addEventListener('scroll', this.scrollMethod)
    },
    methods: {
      scrollMethod () {
        let sumH = document.body.scrollHeight
        let viewH = document.documentElement.clientHeight
        let scrollH = document.body.scrollTop === 0 ? document.documentElement.scrollTop : document.body.scrollTop
        if (viewH + scrollH > sumH) {
          this.getData()
        }
      },
      getData () {
        this.limit += 5
        for (var i = 0; i < 5; i++) {
          this.answers.push({
            username: '罗宇侠',
            userAvatar: 'http://localhost:8080/img/ueditor/20171011/1507732693731046207.jpg',
            answerContent: '来自罗宇侠的答案'
          })
        }
      },
      getQuestion () {
        const questionId = this.$route.params.questionId
        let _this = this
        getQuestion(questionId).then((response) => {
          _this.question.questionTitle = response.result.questionTitle
          _this.question.questionContent = response.result.questionContent
          _this.question.questionId = response.result.questionId
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
      }
    }
  }
</script>
