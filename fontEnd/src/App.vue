<template>
  <div id="app">
    <transition name="fade"
                mode="out-in">
      <router-view></router-view>
    </transition>
  </div>
</template>
<style>
  html,body{background: #f1efe2;}
  img {vertical-align: middle}
  .code {
    overflow-x: scroll;
  }
</style>
<script>
export default {
  name: 'app'
}
</script>
<style lang="less">
  [class^="el-icon-fa"], [class*=" el-icon-fa"] {
    display: inline-block;
    font: normal normal normal 20px/1 FontAwesome!important;
    font-size: inherit;
    text-rendering: auto;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
  };

  @import url("../node_modules/font-awesome/less/font-awesome");
  @fa-css-prefix: el-icon-fa;
</style>
<style>
  * {
    margin: 0;
    padding: 0;
    border: 0;
    vertical-align: baseline;
    word-wrap: break-word;
    box-sizing:border-box;
    font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif;
    line-height: 1.7;
  }
  #verification_code_id div,span{
    box-sizing: content-box;
  }
  ol, ul {
    list-style: none;
  }
  a{
    text-decoration: none;
  }

.fade-enter-active,
.fade-leave-active {
  transition: all .2s ease;
}

.fade-enter,
.fade-leave-active {
  opacity: 0;
}
</style>
