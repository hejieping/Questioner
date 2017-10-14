<template>
  <el-dialog size="large" :visible.sync="dialogVisible">
    <div id="userinfo" style="position: fixed; top: 10px; margin-bottom: 20px">
      <h3>感谢您的回答</h3>
      <img class="useravatar" :src="userInfo.useravatar"/>
      <span>{{ userInfo.username }}</span>
    </div>
    <div style="margin-top: 20px">
      <div>
        <ue :fullscreen="false" :config="config" ref="ue"></ue>
      </div>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click.prevent="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click.prevent="dialogVisible = false">确 定</el-button>
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
    components: {'ue': UE},
    data () {
      return {
        dialogVisible: false,
        config: {
          initialFrameWidth: 900,
          initialFrameHeight: 250
        }
      }
    },
    props: {
      userInfo: {
        type: Object,
        default: function () {
          return {
            username: '罗宇侠',
            useravatar: ''
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
      },
      close () {
        if (this.dialogVisible) {
          this.$emit('dialogClose')
        }
        this.dialogVisible = false
      }
    }
  }

</script>
