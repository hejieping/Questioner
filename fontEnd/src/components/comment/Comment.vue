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
    left: 80px;
  }
</style>

<template>
  <div class="comment">
    <i class="triangle"></i>
    <ul>
      <li v-for="comment in commentsList" class="commentLi">
        <div class="commentThumbnail">
          <img :src="comment.thubnail"/>
        </div>
        <div class="commentMain">
          <a>{{ comment.author }}</a>
          <p class="paragraph"> {{ comment.paragraph }}</p>
          <div class="commentInfo">
            <div class="option">
              <span> {{ comment.commentTime  | moment("ddd, MMM Do YYYY") }}</span>
              <span><i></i> 回复 </span>
              <span><i></i> 赞 </span>
              <span><i></i> 踩 </span>
              <span><i></i> 举报 </span>
            </div>
            <div class="thumbupCount"> {{ comment.thumbUp | formatThumbup }}</div>
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
  export default({
    data () {
      return {
        commentsList: [],
        newComment: ''
      }
    },
    filters: {
      formatThumbup: function (times) {
        return times + '赞'
      }
    }
  })
</script>
