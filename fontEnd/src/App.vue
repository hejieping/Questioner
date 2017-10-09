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
</style>
<script>
export default {
  name: 'app'
}
</script>

<style>
  * {
    margin: 0;
    padding: 0;
    border: 0;
    vertical-align: baseline;
    word-wrap: break-word;
    box-sizing:border-box;
    font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif;
    font-size: 13px;
    line-height: 1.7;
  }
  h1{
    font-family: 'Raleway',Arial,sans-serif;
    font-weight: 700;
    font-size: 45px;
    margin-top: 15px;
    color: #38935f;
  }
  h3{
    font-family: 'Raleway',Arial,sans-serif;
    font-weight: 400;
    font-size: 25px;
    margin-top: 15px;
    color: #918e84;
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
