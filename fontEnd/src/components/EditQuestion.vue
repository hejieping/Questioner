<template>
  <div class="components-container">
    <div id="question-panel">
      <el-form :model="questionForm" ref="questionForm" :rules="rules" label-width="80px">
        <el-form-item prop="questionTitle" label="问题标题">
          <el-input v-model="questionForm.questionTitle" placeholder="输入问题标题能使问题更快被解决哦"></el-input>
        </el-form-item>
        <el-form-item prop="questionType" label="问题类型">
          <el-select v-model="questionForm.questionType" filterable placeholder="请选择">
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
      <el-button style="float: right; margin-bottom: 10px"  size="small" type="success"  @click="submitQuestion('questionForm')">发布问题</el-button>
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
    margin-top: 10px;
  }
  .editor-container{
    z-index: -999;
  }
</style>
<script>
  import UE from '../components/UE.vue'
  import { raiseQuestion } from '@/api/question'
  import { Message } from 'element-ui'
  export default {
    components: {UE},
    data () {
      return {
        defaultMsg: '',
        config: {
          initialFrameWidth: null,
          initialFrameHeight: 250
        },
        questionForm: {
          questionTitle: '',
          questionType: ''
        },
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
        }],
        rules: {
          questionTitle: [
            { required: true, message: '请输入问题标题', trigger: 'blur' }
          ],
          questionType: [
            { required: true, message: '请选择问题类型', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      submitQuestion (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let editor = this.$refs.ue.getUEditor()
            var _this = this
            editor.getKfContent(function (content) {
              raiseQuestion(_this.questionForm.questionTitle, content, editor.getContentTxt())
                .then((response) => {
                  if (response.status === '201') {
                    Message({
                      message: '问题发布成功！',
                      type: 'success',
                      duration: 5 * 1000
                    })
                  }
                }).catch(() => {
                  Message({
                    message: '对不起，问题发布失败！',
                    type: 'error',
                    duration: 5 * 1000
                  })
                })
            })
          } else {
            return false
          }
        })
      }
    }
  }
</script>
