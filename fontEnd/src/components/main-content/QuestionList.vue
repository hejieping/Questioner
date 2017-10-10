<template>
  <div id="question-list">
    {{message}}
  </div>
</template>
<style>

</style>
<script>
  export default {
    data () {
      return {
        message: 'Hello World'
      }
    },
    created () {
      this.updateQuestionType()
    },
    methods: {
      updateQuestionType () {
        this.message = this.$route.params.questionType || '所有问题'
      }
    },
    watch: {
      '$route' (to, from) {
        this.updateQuestionType()
      }
    }
  }
</script>
