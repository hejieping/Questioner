<template>
  <div id="questionContent" v-loading='questionLoading' element-loading-text="拼命加载中">
    <div id="question-top-panel">
      <div class="question-title">
        <h3>
          {{ question.questionTitle }}
        </h3>
      </div>
      <div class="question-info">
        <el-tooltip v-if="hasFollow" content="取消关注后将不会获得更新提醒" placement="top">
          <el-button :disabled="isSendingFollow" @click.prevent="unFollowQuestion()"  size="small" type="success">取消关注</el-button>
        </el-tooltip>
        <el-tooltip v-else content="关注之后将获得更新提醒" placement="top">
          <el-button :loading="loadingFollowStatus" :disabled="isSendingFollow" @click.prevent="followQuestion()"  size="small" type="success">关注问题</el-button>
        </el-tooltip>
        <el-tooltip content="为该问题贡献自己的答案吧" placement="top">
          <el-button @click.prevent="writeAnswer()" size="small" icon="edit" type="info">写回答</el-button>
        </el-tooltip>
      </div>
      <div @mouseover="setShareUrl()" class="jiathis_style" style="clear: both; float: right; margin-right: 80px; margin-top: 10px">
        <span class="jiathis_txt">分享到：</span>
        <a class="jiathis_button_cqq"></a>
        <a class="jiathis_button_weixin"></a>
        <a class="jiathis_button_qzone"></a>
        <a class="jiathis_button_tsina"></a>
        <a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
      </div>
    </div>
    <div id="question-detail" v-html="question.questionContent">
    </div>
    <answer-dialog :userInfo="user" @submitAnswer="submit" ref="dialog"></answer-dialog>
  </div>
</template>
<style scoped>
  #questionContent{
    border: 1px solid #ddd;
    background: #f9fafc;
    padding: 2rem;
    margin: 1rem;
  }

  #question-detail{
    clear: both;
    margin-top: 70px;
    overflow-x: auto;
    overflow-y: hidden;
  }

  .question-title{
    float: left;
    width: 60%;
  }
  .question-info{
    float: right;
    width: 20%;
  }
  .question-info span {
    width: 100%;
  }

</style>
<script>
  import { getQuestion, unFollowQuestion, followQuestion, hasFollowQuestion, postAnswer } from '@/api/question'
  import '../../../static/UE/ueditor.parse'
  import { Message } from 'element-ui'
  import AnswerInputDialog from '@/components/answer/AnswerInputDialog'
  import bus from '../../assets/eventBus.js'
  import { mapGetters } from 'vuex'
  import { initShare } from '@/utils/util'
  export default {
    components: { 'answer-dialog': AnswerInputDialog },
    props: {
      isAllAnswer: {
        type: Boolean,
        default: true
      }
    },
    data () {
      return {
        question: {
          questionTitle: '',
          questionContent: '',
          questionId: null
        },
        hasFollow: false,
        questionLoading: false,
        userId: 1,
        questionId: null,
        isSendingFollow: false,
        loadingFollowStatus: false
      }
    },
    mounted () {
      this.getQuestion()
      let _this = this
      bus.$off('loginSuccess')
      bus.$on('loginSuccess', function () {
        _this.getFollowStatus()
      })
      initShare()
    },
    computed: {
      ...mapGetters(['hasLogin', 'user'])
    },
    methods: {
      unFollowQuestion () {
        const questionId = this.question.questionId
        this.isSendingFollow = true
        unFollowQuestion(questionId).then((response) => {
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
        if (!this.hasLogin) {
          bus.$emit('requestLogin')
          return
        }
        const questionId = this.question.questionId
        this.isSendingFollow = true
        followQuestion(questionId).then((response) => {
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
      },
      getQuestion () {
        this.questionLoading = true
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
            _this.$emit('onPublisherId', response.result.publisher.id)
          })
        }).catch((e) => {
          Message({
            message: '获取问题失败！',
            type: 'error',
            duration: 5 * 1000
          })
        })
      },
      getFollowStatus () {
        if (!this.hasLogin) {
          return
        }
        this.loadingFollowStatus = true
        const questionId = this.question.questionId
        hasFollowQuestion(questionId).then((response) => {
          this.hasFollow = response.result
          this.loadingFollowStatus = false
        }).catch((e) => {
          Message({
            message: '获取信息失败，请稍后重试！',
            type: 'error',
            duration: 1000
          })
        })
      },
      submit: function (answer) {
        postAnswer(answer, this.question.questionId).then((response) => {
          Message({
            message: '感谢你的答案!',
            type: 'success',
            duration: 5 * 1000
          })
          if (this.isAllAnswer) {
            this.$emit('getAnswer')
          }
        }).catch((e) => {
          Message({
            message: '对不起，回答失败，请稍后再试！',
            type: 'error',
            duration: 5 * 1000
          })
        })
      },
      writeAnswer () {
        if (!this.hasLogin) {
          bus.$emit('requestLogin')
          return
        }
        this.$refs.dialog.open()
      },
      setShareUrl () {
        window.jiathis_config = window.location.host + '/questionDetail/' + this.questionId
      }
    }
  }
</script>
