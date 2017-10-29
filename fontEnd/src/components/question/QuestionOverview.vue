<template>
  <div>
  <div class="publisherInfo">
    <router-link :to="{ path: `/user/${questionOverview.publisherId}` }">
      <img class="avatar" :src="questionOverview.publisherImgSrc">
      <span class=".publisher"> {{ questionOverview.publisherName }} </span>
    </router-link>
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
      发布于 <span style="padding-right: 5px">{{ questionOverview.publishDateTime | moment("YYYY-MM-DD ") }} </span>
    </div>
  </div>
  <div class="info">
    <div class="views">
      <el-tag type="gray"> {{ questionOverview.views }} 浏览</el-tag>
    </div>
    <div class="answers">
      <el-tag type="gray">{{ questionOverview.answers }}回答</el-tag>
    </div>
    <div class="solved-views">
      <el-tag v-if="questionOverview.solved" type="success">已解决</el-tag>
      <el-tag v-else type="gray">未解决</el-tag>
    </div>
  </div>
  </div>
</template>
<style scoped>
  .publisherInfo{
    float: left;
    margin-right: 10px;
    width: 75px;
    text-align: center;
    height: 100%;
    min-width: 50px;
    cursor: pointer;
  }
  .publisherInfo .avatar{
    width: 50px;
    height: 50px;
    margin: 0 auto;
    vertical-align: middle;
    display: block;
    border: green solid 1px;
  }
  .summary{
    padding: 5px;
    float: left;
    margin-right: 10px;
    width: 70%;
    min-height: 50px;
    border-right: solid grey 1px;
    border-left: solid grey 1px;
  }
  .summary h3{
    overflow: hidden;
  }
  .summary a {
    color: #38935f;
  }
  .summary .publish-date{
    clear: left;
    float: right;
  }
  .info{
    float: right;
    width: 15%;
  }
  .info>div {
    margin-bottom: 5px;
  }
</style>
<script>
  export default {
    props: {
      questionOverview: Object
    }
  }
</script>
