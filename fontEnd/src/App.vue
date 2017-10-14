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
    line-height: 1.7;
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
