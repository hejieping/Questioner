<template>
  <div class="answer">
    <div>
      <router-link class="user-info" :to="{ path: `/user/${answer.account.id}/` }">
        <span><img :src="answer.account.avatarURL"   class="user-avatar"/></span>
        <span>{{ answer.account.username }}</span>
      </router-link>
      发布于 <span>{{ $moment(answer.answerDateTime).fromNow() }}</span>
    </div>
    <div class="answer-info" v-html="answer.answerContent">
    </div>
    <div class="feedback">
      <el-tooltip effect="dark" content="答案对人有帮助，有参考价值" placement="top">
        <span @click="giveFeedback(true)" ><i class="fa fa-thumbs-o-up fa-lg"></i> {{ answer.thumbsUpCount }} </span>
      </el-tooltip>
      <el-tooltip effect="dark" content="答案没帮助，是错误的答案，答非所问" placement="top">
        <span @click="giveFeedback(false)" ><i class="fa fa-thumbs-o-down fa-lg"></i> {{ answer.thumbsDownCount }} </span>
      </el-tooltip>
      <span @click="toggleComment()"  class="comment"><i class="fa  fa-comments-o fa-lg"></i>
            <span v-show="!showComment">{{ answer.commentCount || 0 }}条评论</span>
            <span v-show="showComment"> 收起评论 </span>
      </span>
      <span v-if="answer.accepted">
        <el-tooltip effect="dark" content="提问者已经采纳了该回答" placement="top">
          <i style="color: green" class="el-icon-circle-check"></i>
        </el-tooltip>
      </span>
      <span v-else="">
        <el-tooltip effect="dark" content="提问者对该答案没有做出反馈" placement="top">
          <i class="el-icon-circle-check"></i>
        </el-tooltip>
      </span>
      <span v-if="showAccept">
        <el-tooltip effect="dark" content="该回答解决了我的问题，采纳该回答" placement="top">
          <i @click="acceptAnswer()"  style="color: green" class="fa fa-check"></i>
        </el-tooltip>
      </span>
      <!-- JiaThis Button BEGIN -->
      <div @mouseover="setShareUrl()" class="jiathis_style" style="float: right; margin-right: 65px">
        <span class="jiathis_txt">分享到：</span>
        <a class="jiathis_button_cqq"></a>
        <a class="jiathis_button_weixin"></a>
        <a class="jiathis_button_qzone"></a>
        <a class="jiathis_button_tsina"></a>
        <a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
      </div><!-- JiaThis Button END -->
    </div>
    <div class="commentDetail" v-show="showComment">
      <comment :answerId="answer.id"></comment>
    </div>
  </div>
</template>
<style scoped>
  .answer{
    border-bottom: 1px solid grey;
    margin-bottom: 10px;
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
  .feedback>span{
    margin-right: 10px;
  }
  .feedback>span{
    cursor: pointer;
  }
  .feedback>span:hover{
    color: #017E66;
  }
  .user-info {
    cursor: pointer;
  }
</style>
<script>
  import Comment from '../comment/Comment.vue'
  import bus from '../../assets/eventBus'
  import { giveAnswerFeedback, acceptAnswer } from '@/api/answer'
  import '../../../static/UE/ueditor.parse'
  import { Message } from 'element-ui'
  import { mapGetters } from 'vuex'
  import { initShare } from '@/utils/util'
  export default {
    components: { 'comment': Comment },
    data () {
      return {
        isFeedback: false,
        showComment: false,
        questionId: null
      }
    },
    props: {
      answer: Object,
      isCurrentUser: Boolean
    },
    computed: {
      showAccept: function () {
        return this.isCurrentUser && !this.answer.accepted
      },
      ...mapGetters(['hasLogin'])
    },
    mounted: function () {
      this.$nextTick(function () {
        window.uParse('.answer', {
          rootPath: '../../static/UE/'
        })
      })
      this.questionId = this.$route.params.questionId
      initShare()
    },
    methods: {
      toggleComment () {
        if (this.showComment === false) {
          bus.$emit('comment_' + this.answer.id)
        }
        this.showComment = !this.showComment
        this.$emit('onToggleComment')
      },
      giveFeedback (isGood) {
        if (this.isFeedback) {
          return
        }
        if (!this.hasLogin) {
          bus.$emit('requestLogin')
          return
        }
        giveAnswerFeedback(this.answer.id, isGood).then((response) => {
          if (response.status === '412') {
            Message({
              message: '您已经对该回答做出反馈了！',
              type: 'info',
              duration: 1000
            })
          } else if (response.status === '200') {
            Message({
              message: '反馈成功！',
              type: 'info',
              duration: 1000
            })
            if (isGood) {
              this.answer.thumbsUpCount += 1
            } else {
              this.answer.thumbsDownCount += 1
            }
          }
          this.isFeedback = false
        }).catch((e) => {
          Message({
            message: '反馈出错，请稍后再试！',
            type: 'error',
            duration: 1000
          })
          this.isFeedback = false
        })
      },
      acceptAnswer () {
        acceptAnswer(this.answer.id).then((response) => {
          if (response.status === '200') {
            Message({
              message: '采纳该回答成功！',
              type: 'success',
              duration: 1000
            })
            this.answer.accepted = true
          } else {
            this.handlerError()
          }
        }).catch((e) => {
          this.handlerError()
        })
      },
      handlerError () {
        Message({
          message: '采纳该回答失败，请稍后重试！',
          type: 'error',
          duration: 1000
        })
      },
      setShareUrl () {
        window.jiathis_config.url = window.location.host + '/questionDetail/' + this.questionId + '/' + this.answer.id
      }
    }
  }
</script>
