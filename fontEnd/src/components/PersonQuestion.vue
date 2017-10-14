<template>
  <div id="questionDetailPanel" v-loading.lock='loading'>
    <h1> {{ question.questionTitle }} </h1>
    <div id="questionContent" v-html="question.questionContent">
    </div>
  </div>
</template>
<style>
  #questionDetailPanel{
    margin: 50px;
  }
</style>
<script>
  import { getQuestion } from '@/api/question'
  import { Message } from 'element-ui'
  import '../../static/UE/ueditor.parse'
  export default {
    data () {
      return {
        question: {
          questionTitle: '',
          questionContent: '',
          questionId: null
        },
        loading: true
      }
    },
    mounted: function () {
      const questionId = this.$route.params.questionId
      let _this = this
      getQuestion(questionId).then((response) => {
        _this.question.questionTitle = response.result.questionTitle
        _this.question.questionContent = response.result.questionContent
        _this.question.questionId = response.result.questionId
        _this.$nextTick(function () {
          window.uParse('#questionContent', {
            rootPath: '../../static/UE/'
          })
          _this.loading = false
        })
      }).catch((e) => {
        Message({
          message: '获取问题失败！',
          type: 'error',
          duration: 5 * 1000
        })
      })
    }
  }
</script>
