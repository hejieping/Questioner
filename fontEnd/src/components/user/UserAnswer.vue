<template>
  <div>
    <h3 class="answers_heading">
      <span>他的回答</span>
      <span class="sort-box" style="float: right">
        <el-radio-group v-model="sortParam">
          <el-radio-button label="时间"></el-radio-button>
          <el-radio-button label="得票"></el-radio-button>
        </el-radio-group>
      </span>
    </h3>
    <div class="answers-container">
      <div class="answer">
        <el-row>
          <el-col :span="20">
            <el-tag type="success"><i class="fa fa-thumbs-o-up"></i> 10</el-tag>
            <el-tag><i class="fa fa-thumbs-o-down"></i> 5 </el-tag>
            <span style="margin-left: 10px">
              <a href="javascript:void 0" class="questionTitle">
                HELLO WORLD
              </a>
            </span>
          </el-col>
          <el-col :span="4" style="text-align: right">
            三天前
          </el-col>
        </el-row>
      </div>
      <div class="answer">
        HELLO
      </div>
      <div class="answer">
        HELLO
      </div>
    </div>
  </div>
</template>
<style scoped>
  .answers_heading {
    padding-bottom: 20px;
    border-bottom: solid 1px grey;
  }
  .answer {
    padding: 10px 0;
    border-bottom: 1px solid grey;
    margin-top: 0;
  }
  .answer a {
    color: #20a0ff;
  }
  .answer a:hover {
    text-decoration: underline;
   }
</style>
<script>
  export default {
    data () {
      return {
        sortParam: '时间'
      }
    }
  }
</script>
