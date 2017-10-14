<template>
  <div id="container">
    <top-header></top-header>
    <transition name="fade"
                mode="out-in">
      <router-view></router-view>
    </transition>
  </div>
</template>
<style scoped>
  #container{
    width: 100%;
    min-height: 100vh;
    position: relative;
    overflow: auto;
  }
</style>
<script>
  import TopHeader from './top-header/Top-Header.vue'
  import Content from './main-content/Content.vue'
  export default {
    data () {
      return {
        message: 'Hello World'
      }
    },
    components: {
      'top-header': TopHeader,
      'question-content': Content
    }
  }
</script>
