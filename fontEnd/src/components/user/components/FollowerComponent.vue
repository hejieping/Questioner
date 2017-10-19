<template>
  <el-row class="follow">
    <el-col :span="20">
      <img class="follower-img" :src="follower.avatarURL"/>
      <div class="followers-info">
        <a @click="$router.push({ path: `/user/${follower.id}/` })" href="javascript:0">{{ follower.username }} </a>
        <div class="follower-reputation">
          <span> {{ follower.creditPoint }} </span>
          <span>声望</span>
        </div>
      </div>
    </el-col>
    <el-col :span="4">
      <el-button @click="follow()" style="float: right" size="small" type="primary" icon="star-on">加关注</el-button>
    </el-col>
  </el-row>
</template>
<style>
  .follower-img {
    width: 38px;
    height: 38px;
    border-radius: 50%;
    float: left;
    margin-right: 10px;
  }
  .follower-info {
    padding-left: 20px;
  }
  .follower-reputation {
    font-size: 14px;
    color: #999999;
    line-height: 20px;
  }
</style>
<script>
  export default {
    props: {
      follower: Object
    },
    methods: {
      follow () {
        alert(this.follower.id)
      }
    }
  }
</script>
