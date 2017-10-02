<template>
  <li class="section">
    <div class="sectionList">
      <i class="remove" @click="remove"></i>
      <div class="thumb-up">
        <img :src='topicType(content.topic)' />
        <button>{{content.thumbUp}}</button>
      </div>
      <div class="list-content">
        <p class="topic">来自话题:&nbsp;{{content.topic}}</p>
        <a class="title" :herf="content.questionLink">{{content.title}}</a>
        <div class="answer">
          <a class="userName" :href="content.userLink">{{content.userName}}</a>
          <span class="userInfo">{{content.userInfo}}</span>
          <div class="main-content">
            <img :src="content.firstImg">
            <p>
              {{content.txt}}
                            <a class="showMore" :href="content.answerLink">显示更多</a>
            </p>
          </div>
        </div>
        <ul class="answerOption">
          <li class="follow">
            <i></i>
            <a @click="followed=!followed">{{followed?"取消关注":"关注专栏"}}</a></li>

          <li class="comment" @click="showComment=!showComment">
            <i></i>
            <span v-show="showComment">{{content.commentsCount}} 条评论</span>
            <span v-show="!showComment">收起评论</span>
          </li>
          <li>
            <ul>
              <li class="share"  v-show="showOther">
                <i></i>
                <a href="">分享</a>
              </li>
              <li class="report" v-show="showOther">
                <span>.</span>
                <a href="">举报</a>
              </li>
              <li class="readMore">
                <span>.</span>
                <a href="">去往文章页</a>
              </li>
            </ul>
          </li>
        </ul>
        <div class="commentDetail" v-show="!showComment">
          <comment></comment>
        </div>
      </div>
    </div>

  </li>
</template>
<style scoped>
  /* 整体布局 */
  .section{
    border-top:1px solid #dcdcdc;
    padding:15px 2px;
  }
  .sectionList{
    display:flex;
    justify-content: space-between;
    position: relative;
  }
  .thumb-up{
    flex:1
  }
  .thumb-up img{
    display:block;
    width:38px;
    height:38px;
    border-radius:3px;
    border:0;
    margin:0 auto 10px;
    overflow: hidden;
  }
  .thumb-up button{
    display: block;
    color: #698ebf;
    height: 24px;
    line-height: 24px;
    width: 38px;
    margin:0 auto;
    border-radius: 3px;
    border: 0;
    cursor: pointer;
    background: #eff6fa;
    text-align: center;
  }
  .list-content{
    flex:12;
  }
  .topic{
    color:#999;
  }
  .title{
    display:block;
    color: #259;
    font-weight:700;
  }
  .title:hover{
    text-decoration: underline;
  }
  .userName{
    color: #111;
    font-weight:700;
    margin-right:15px;
  }
  .userName:hover{
    text-decoration: underline;
  }
  .answer div img{
    float: left;
    height: 112px;
    width: 200px;
    border-radius: 4px;
    margin: 1px 10px 5px 0;
  }
  .showMore{
    background-color: #eff6fa;
    padding: 0 3px;
    border-radius: 2px;
    font-size: 12px;
    color:#259;
  }
  .answerOption{
    clear:both;
  }
  .answerOption li{
    display:inline-block;
    margin-right:8px;
    cursor: pointer;
  }
  .answerOption li i {
    display:inline-block;
    line-height:16px;
    width: 10px;
    height: 10px;
    background:url('./img/sprites-1.9.2.4c54885a.png') no-repeat;
  }
  .answerOption li.follow i{
    background-position: -97px -23px;
  }
  .answerOption li.comment i {
    background-position:-28px -22px;
  }
  .answerOption li.comment span:hover{
    text-decoration: underline;
    color: #259;
  }
  .answerOption li.share i {
    background-position:-68px -22px;
  }
  li.report span,li.readMore span{
    display:inline-block;
    font-weight:800;
    height:1px;
    position:relative;
    top:-4px;
  }
  i.remove{
    background:url('./img/sprite-icons-2.png') no-repeat -79px -48px;
    position:absolute;
    top:10px;
    right:10px;
    color:#000;
    cursor: pointer;
    display:inline-block;
    width: 20px;
    height: 20px;
  }

  .commentDetail{
    position: relative;
  }
</style>
<script>
  import Comment from './Comment.vue'

  export default({
    data () {
      return {
        showOther: true,
        showComment: true,
        followed: true
      }
    },
    components: {
      'comment': Comment
    },
    props: [
      'content'
    ],
    methods: {
      topicType  (topic) {
        switch (topic) {
          case '旅行':
            return ('./static/assets/img/travel.jpg')
          case '互联网':
            return ('./static/assets/img/internet.jpg')
          case '健身':
            return ('./static/assets/img/workout.jpg')
          case '美食':
            return ('./static/assets/img/food.jpg')
          default:
            return ('./static/assets/img/internet.jpg')
        }
      },
      remove () {
        this.$emit('remove')
      }
    }
  })

</script>














