<template>
  <div class="components-container">
    <div id="question-panel">
      <el-form :model="questionForm" ref="questionForm" :rules="rules" label-width="80px">
        <el-form-item prop="questionTitle" label="问题标题">
          <el-input v-model="questionForm.questionTitle" placeholder="输入问题标题能使问题更快被解决哦"></el-input>
        </el-form-item>
        <el-form-item prop="questionType" label="问题类型">
          <el-select v-model="questionForm.questionType" filterable placeholder="请选择">
            <el-option-group
              v-for="group in groupQuestionType"
              :key="group.label"
              :label="group.label">
              <el-option
                v-for="item in group.options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-option-group>
          </el-select>
        </el-form-item>
        <el-form-item label="问题描述">
          <div class="editor-container">
            <UE  :config="config" ref="ue"></UE>
          </div>
        </el-form-item>
      </el-form>
      <el-button :disabled="isSendingRequest" style="float: right; margin-bottom: 10px"  size="small" type="success"  @click="submitQuestion('questionForm')">发布问题</el-button>
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
  import UE from '../UE.vue'
  import { raiseQuestion, getQuestionType } from '@/api/question'
  import { Message } from 'element-ui'
  import { transformQuestionType2Map } from '@/utils/util'
  export default {
    components: {UE},
    data () {
      return {
        defaultMsg: '',
        questionTypeArray: [],
        config: {
          initialFrameWidth: null,
          initialFrameHeight: 250
        },
        questionForm: {
          questionTitle: '',
          questionType: ''
        },
        rules: {
          questionTitle: [
            { required: true, message: '请输入问题标题', trigger: 'blur' }
          ],
          questionType: [
            { required: true, message: '请选择问题类型', trigger: 'blur' }
          ]
        },
        isSendingRequest: false
      }
    },
    computed: {
      groupQuestionType () {
        const questionTypeMap = transformQuestionType2Map(this.questionTypeArray)
        let options = []
        for (var key in questionTypeMap) {
          let option = []
          let typeArray = questionTypeMap[key]
          for (var i = 0; i < typeArray.length; i++) {
            option.push({
              label: typeArray[i]['course'],
              value: '' + typeArray[i]['id']
            })
          }
          options.push({
            label: key,
            options: option
          })
        }
        return options
      }
    },
    mounted: function () {
      getQuestionType().then((response) => {
        if (response.status === '200') {
          this.questionTypeArray = response.result
        }
      })
    },
    methods: {
      submitQuestion (formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let editor = this.$refs.ue.getUEditor()
            var _this = this
            this.isSendingRequest = true
            editor.getKfContent(function (content) {
              raiseQuestion(_this.questionForm.questionTitle, _this.questionForm.questionType, content, editor.getContentTxt())
                .then((response) => {
                  if (response.status === '201') {
                    Message({
                      message: '问题发布成功, 可在个人中心看到此问题！',
                      type: 'success',
                      duration: 5 * 1000
                    })
                    _this.resetFields()
                    _this.isSendingRequest = false
                  }
                }).catch((e) => {
                  Message({
                    message: '对不起，问题发布失败！',
                    type: 'error',
                    duration: 5 * 1000
                  })
                  _this.isSendingRequest = false
                  _this.resetFields()
                })
            })
          } else {
            return false
          }
        })
      },
      resetFields () {
        let editor = this.$refs.ue
        editor.clearContent()
        this.questionForm.questionTitle = ''
        this.questionForm.questionType = ''
      }
    }
  }
</script>
