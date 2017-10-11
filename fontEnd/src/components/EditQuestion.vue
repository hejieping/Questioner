<template>
  <div class="components-container">
    <div>
      <el-form label-width="80px">
        <el-form-item label="问题标题">
          <el-input v-model="questionTitle" placeholder="输入问题标题能使问题更快被解决哦"></el-input>
        </el-form-item>
        <el-form-item label="问题类型">
          <el-select v-model="questionType" filterable placeholder="请选择">
            <el-option
              v-for="item in questionTypeArray"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="问题描述">
          <div class="editor-container">
            <UE :defaultMsg="defaultMsg" :config="config" ref="ue"></UE>
          </div>
        </el-form-item>
      </el-form>
      <el-button style="float: right"  size="small" type="success"  @click="getUEContent()">发布问题</el-button>
    </div>
  </div>
</template>
<style scoped>
  .components-container{
    margin-left: 5%;
    margin-right: 5%;
    width: 90%;
  }
  .components-container>div{
    margin-top: 5px;
  }
  .editor-container{
    z-index: -999;
  }
</style>
<script>
  import UE from '../components/UE.vue'
  export default {
    components: {UE},
    data () {
      return {
        defaultMsg: '',
        config: {
          initialFrameWidth: null,
          initialFrameHeight: 250
        },
        questionTitle: '',
        questionType: '',
        questionTypeArray: [{
          value: '选项1',
          label: '黄金糕'
        }, {
          value: '选项2',
          label: '双皮奶'
        }, {
          value: '选项3',
          label: '蚵仔煎'
        }, {
          value: '选项4',
          label: '龙须面'
        }, {
          value: '选项5',
          label: '北京烤鸭'
        }]
      }
    },
    methods: {
      getUEContent () {
        let editor = this.$refs.ue.getUEditor()
        var _this = this
        editor.getKfContent(function (content) {
          _this.$notify({
            title: '获取成功，可在控制台查看！',
            message: content,
            type: 'success'
          })
        })
      }
    }
  }
</script>
