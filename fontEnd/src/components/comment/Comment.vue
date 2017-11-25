<style scoped>
  .comment{
    display: block;
    padding: 5px 12px;
    border: 1px solid #ddd;
    margin-top: 10px;
    margin-bottom: 10px;
    background: white;
  }
  .comment ul li{
    width: 100%;
    padding: 8px 2px;
    border-bottom: 1px solid #ddd;
  }
  .paragraph{
    min-height: 22px;
    word-wrap: break-word;
    color: #222;
  }
  .commentInfo{
    display: flex;
    justify-content: space-between;
  }
  .commentLi{
    display: flex;
  }
  .commentThumbnail {
    flex: 1px;
    text-align: center;
    cursor: pointer;
  }
  .commentThumbnail img {
    width: 38px;
    height: 38px;
  }
  .commentMain{
    flex: 11;
  }
  .commentMain a {
    color: #259;
    font-weight: 600;
    font-size: 15px;
  }
  .option{
    flex: 8;
    font-size: 10px;
  }
  .thumbupCount{
    flex: 1;
    text-align: right;
    font-size: 10px;
  }
  .myComment input{
    width: 90%;
    padding: 9px 12px;
    height: 38px;
    line-height: 1.4;
    color: #222;
    border: 1px solid #ddd;
    border-radius: 4px;
    -webkit-box-shadow: inset 0 1px 3px rgba(0,0,0,.1);
    -moz-box-shadow: inset 0 1px 3px rgba(0,0,0,.1);
    box-shadow: inset 0 1px 3px rgba(0,0,0,.1);
    margin: 10px 0;
    outline: 0;
  }
  .myComment button{
    width: 10%;
    float: right;
    height: 38px;
    margin: 10px 0;
  }
  .triangle{
    display: inline-block;
    width: 13px;
    height: 8px;
    background: url('../../assets/img/sprites-1.9.2.4c54885a.png') no-repeat -200px -165px;
    position: relative;
    top: -25px;
    left: 100px;
  }
</style>

<template>
  <div class="comment" v-loading.lock="isLoadingComment" element-loading-text="玩命加载中">
    <i class="triangle"></i>
    <ul>
      <li v-for="comment in commentsList" class="commentLi">
        <div class="commentThumbnail">
          <router-link :to="{ path: `/user/${comment.commenter.id}/` }">
            <img :src="comment.commenter.avatarURL"/>
          </router-link>
        </div>
        <div class="commentMain">
          <router-link :to="{ path: `/user/${comment.commenter.id}/` }">{{ comment.commenter.username }}</router-link>
          <p class="paragraph"> {{ comment.commentContent }}</p>
          <div class="commentInfo">
            <div class="option">
              <span> {{ comment.commentDateTime  | moment("ddd, MMM Do YYYY") }}</span>
              <span><i></i> 回复 </span>
              <span><i></i> 赞 </span>
              <span><i></i> 踩 </span>
              <span><i></i> 举报 </span>
            </div>
            <div class="thumbupCount"> {{ comment.thumbsUpCount | formatThumbup }}</div>
          </div>
        </div>
      </li>
    </ul>
    <div class="pages">

    </div>
    <div class="myComment">
      <input type="text" placeholder="请写下您的评论" v-model="newComment"
      @keyup.enter="addNewComment"/>
      <el-button @click.prevent="addNewComment"  size="small" type="danger" icon="edit">发送评论</el-button>
    </div>
  </div>
</template>
<script>
  import { postAnswerComment, getAnswerCommentsOfAnswer } from '@/api/answerComment'
  import { Message } from 'element-ui'
  import bus from '../../assets/eventBus'
  import { mapGetters } from 'vuex'
  export default {
    data () {
      return {
        commentsList: [],
        newComment: '',
        isLoadingComment: true
      }
    },
    props: {
      answerId: {
        type: Number
      }
    },
    computed: {
      ...mapGetters(['hasLogin', 'user'])
    },
    mounted: function () {
      const eventName = 'comment_' + this.answerId
      bus.$off(eventName)
      bus.$on(eventName, this.getComment)
    },
    methods: {
      addNewComment () {
        if (!this.hasLogin) {
          bus.$emit('requestLogin')
          return
        }
        postAnswerComment(this.answerId, this.newComment).then((response) => {
          if (response.status === '201') {
            Message({
              message: '发表评论成功！',
              type: 'success',
              duration: 1000
            })
            this.commentsList.push({
              commentContent: this.newComment,
              thumbsUpCount: 0,
              commentDateTime: new Date(),
              commenter: {
                username: this.user.username,
                avatarURL: this.user.avatarURL,
                id: this.user.id
              }
            })
            this.newComment = ''
          }
        }).catch((e) => {
          Message({
            message: '发表评论失败，请稍后再试！',
            type: 'error',
            duration: 1000
          })
        })
      },
      getComment () {
        if (this.commentsList.length > 0) {
          return
        }
        this.isLoadingComment = true
        getAnswerCommentsOfAnswer(this.answerId).then((response) => {
          this.commentsList = response.result
          this.isLoadingComment = false
        }).catch((e) => {
          Message({
            message: '获取评论失败，请稍后再试！',
            type: 'error',
            duration: 1000
          })
        })
      }
    },
    filters: {
      formatThumbup: function (times) {
        return times + '赞'
      }
    }
  }
</script>
