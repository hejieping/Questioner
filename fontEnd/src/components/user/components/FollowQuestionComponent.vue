<template>
  <el-row class="follow">
    <el-col :span="18">
      <div class="question-info">
        <a @click="$router.push({ path: `/questionDetail/${question.id}/` })" href="javascript:0">{{ question.questionTitle }} </a>
        <div class="questionType-info">
          <el-tag type="gray"> {{ question.questionType.subject }}</el-tag>
          <el-tag> {{ question.questionType.course }} </el-tag>
        </div>
      </div>
    </el-col>
    <el-col :span="6">
      <div style="float: right">
        <el-tag>{{ question.views }} 次浏览</el-tag>
        <el-button @click="follow()" style="float: right;margin-left: 10px" size="small" type="primary" icon="star-on">加关注</el-button>
      </div>
    </el-col>
  </el-row>
</template>
<style>
</style>
<script>
  export default {
    props: {
      question: Object
    },
    methods: {
      follow () {
        alert(this.question.id)
      }
    }
  }
</script>
