<template>
  <div id="home-container">
    <div class="panel panel-default user-profile">
      <div class="panel-heading">
        <h3>用户画像</h3>
      </div>
      <div class="panel-body">
        <div class="diagram">
          <div class="content" style="margin-top: 75px; margin-left: 45%">
            TODO
          </div>
        </div>
      </div>
    </div>
    <div class="panel panel-default user-activity">
      <div class="panel-heading">
        <h3> 个人动态 </h3>
      </div>
      <div class="panel-body">
        <div class="user-activity" style="height: 150px">
          <div class="content" style="margin-top: 75px; margin-left: 45%">
            TODO
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<style>
  .panel-default {
    border-color: #ddd;
  }
  .panel {
    margin-bottom: 20px;
    background-color: #fff;
    border: 1px solid transparent;
    border-radius: 4px;
    -webkit-box-shadow: 0 1px 1px rgba(0,0,0,0.05);
    box-shadow: 0 1px 1px rgba(0,0,0,0.05);
  }
  .panel-default>.panel-heading {
    color: #333;
    background-color: #f5f5f5;
    border-color: #ddd;
  }
  .panel-heading {
    padding: 10px 15px;
    border-bottom: 1px solid transparent;
    border-top-right-radius: 3px;
    border-top-left-radius: 3px;
  }
  .panel-body {
    padding: 15px;
  }
  .user-profile .diagram {
    height: 150px;
  }
</style>
<script>
  export default {
    data () {
      return {
        userId: ''
      }
    },
    mounted () {
      this.userId = this.$route.params.userId
    }
  }
</script>
