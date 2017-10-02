<style scoped>


  .comment{
    display:block;
    padding:5px 12px;
    border:1px solid #ddd;
    border-radius:8px;
    margin-top:10px;
  }
  .comment ul li{
    width:100%;
    padding:8px 2px;
    border-bottom:1px solid #ddd;
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
    display:flex;
  }
  .commentThumbnail{
    flex:1;
    text-align: center;
  }
  .commentMain{
    flex:11;
  }
  .commentMain a{
    color:#259;
    font-weight:600;
    font-size: 15px;
  }
  .option{
    flex:8;
    font-size: 10px;
  }
  .thumbupCount{
    flex:1;
    text-align: right;
    font-size: 10px;
  }
  .myComment input{
    width: 100%;
    padding: 9px 12px;
    height:38px;
    line-height: 1.4;
    color: #222;
    border: 1px solid #ddd;
    border-radius: 4px;
    box-shadow: inset 0 1px 3px rgba(0,0,0,.1);
    margin:10px 0;
    outline:0;
  }
  .triangle{
    display: inline-block;
    width:13px;
    height:8px;
    background: url('../../assets/img/sprites-1.9.2.4c54885a.png') no-repeat -200px -165px;;
    position:relative;
    top:-20px;
    left:100px;
  }
</style>
<template>
  <div class="comment">
    <i class="triangle"></i>
    <ul>
      <li v-for="comment in commentsList" class="commentLi">
        <div class="commentThumbnail">
          <img :src="comment.thumbnail"/>
        </div>
        <div class="commentMain">
          <a> {{ comment.author }}</a>
          <p class="paragraph">{{comment.paragraph}}</p>
          <div class="commentInfo">
            <div class="option">
              <span>{{comment.commentTime | formatTime}}</span>
              <span><i></i>回复</span>
              <span><i></i>赞</span>
              <span><i></i>踩</span>
              <span><i></i>举报</span>
            </div>
            <div class="thumbupCount">{{ comment.thumbUp| formatThubmup }} </div>
          </div>
        </div>
      </li>
    </ul>
    <div class="page">

    </div>
    <div class="myComment">
      <input type="text" placeholder="请写下你的评论" v-model="newComment"
      @keyup.enter="pushNewComment"/>
    </div>
  </div>
</template>
<script>
  import fetch from '@/utils/fetch'
  export default({
    data () {
      return {
        commentsList: [],
        newComment: ''
      }
    },
    filters: {
      formatTime: function (time) {
        let now = new Date()
        let between = Math.floor((now - time) / (1000 * 3600 * 24))
        if (between >= 1) {
          return between + '天前'
        }
        return Math.floor((now - time) / (1000 * 3600)) + '小时前'
      },
      formatThumbup: function (times) {
        return times + '赞'
      }
    },
    methods: {
      getComment () {
        fetch(
          {
            url: '/data/comment.json',
            method: 'GET'
          }
        )
          .then((res) => {
            this.commentsList = res
          })
      },
      pushNewComment () {
        if (this.newComment !== '') {
          let now = new Date()
          this.commentsList.push(
            {'author': 'Elon',
              'thumbnail': './static/assets/img/user.jpg',
              'paragraph': this.newComment,
              'commentTime': now,
              'thumbUp': 0
            }
            )
          this.newComment = ''
        }
      }
    },
    mounted: function () {
      this.$nextTick(() => {
        this.getComment()
      })
    }
  })
</script>
