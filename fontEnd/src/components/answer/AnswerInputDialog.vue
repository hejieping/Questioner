<template>
  <el-dialog size="large" :visible.sync="dialogVisible">
    <div v-if="userInfo" id="userinfo" style="position: fixed; top: 10px; margin-bottom: 20px">
      <h3>感谢您的回答</h3>
      <img class="useravatar" :src="userInfo.avatarURL"/>
      <span>{{ userInfo.username }}</span>
    </div>
    <div style="margin-top: 20px">
      <div>
        <UE :fullscreen="false"  :config="config" ref="ue"></UE>
      </div>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click.prevent="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click.prevent="submitAnswer()">确 定</el-button>
    </div>
  </el-dialog>
</template>
<style scoped>
  #userinfo .useravatar{
    width: 38px;
    height: 38px;
    vertical-align: top;
    margin-bottom: 20px;
    margin-right: 20px;
  }
</style>
<script>
  import UE from '@/components/UE.vue'
  export default {
    components: { UE },
    data () {
      return {
        dialogVisible: false,
        config: {
          initialFrameWidth: 1100,
          initialFrameHeight: 250
        },
        editorReady: false
      }
    },
    props: {
      userInfo: {
        type: Object,
        default: function () {
          return {
            username: '',
            avatarURL: ''
          }
        }
      }
    },
    methods: {
      open () {
        if (!this.dialogVisible) {
          this.$emit('dialogOpen')
        }
        this.dialogVisible = true
        let _this = this
        this.$nextTick(function () {
          let ueditor = _this.getEditor()
          ueditor.addListener('ready', function () {
            ueditor.setContent('') // 确保UE加载完成后，放入内容。
            _this.editorReady = true
          })
        })
        if (_this.editorReady) {
          this.getEditor().setContent('')
        }
      },
      close () {
        if (this.dialogVisible) {
          this.$emit('dialogClose')
        }
        this.dialogVisible = false
      },
      getEditor () {
        return this.$refs.ue.getUEditor()
      },
      submitAnswer () {
        this.$emit('submitAnswer', this.getEditor().getContent())
        this.dialogVisible = false
      }
    }
  }

</script>
