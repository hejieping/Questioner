<template>
  <div id="app">
    <transition name="fade"
                mode="out-in">
      <router-view></router-view>
    </transition>
  </div>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style>
   *{
    /*css reset*/
    margin:0;
    padding:0;
    list-style:none;
    box-sizing:border-box;
    font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif;
    font-size: 13px;
    line-height: 1.7;
    color:#666;
    text-align: left;
  }
  a{
    cursor: pointer;
    text-decoration:none;
  }
  a:hover{
    text-decoration: underline;
    color:#259;
  }
  .left{
    float:left;
  }
  .right{
    float:right;
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
