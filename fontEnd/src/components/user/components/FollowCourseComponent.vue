<template>
  <div class="follow-courses">
    <el-row class="follow course">
      <el-col :span="20">
        <el-tag> {{ course.subject }} -> {{ course.course }} </el-tag>
      </el-col>
      <el-col :span="4">
        <el-button @click="follow()" style="float: right" size="small" type="primary" icon="star-on">加关注</el-button>
      </el-col>
    </el-row>
  </div>
</template>
<style>

</style>
<script>
  export default {
    props: {
      course: Object
    },
    methods: {
      follow () {
        alert(this.course.id)
      }
    }
  }
</script>
