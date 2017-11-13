<template>
  <el-dialog title="用户登录" :visible.sync="dialogVisible">
    <el-form ref="loginform" :model="loginform" :rules="loginrule">
      <el-form-item  prop="username" label="账号" label-width="50px">
        <el-input type="text" icon="edit" autofocus placeholder="请输入账号" v-model="loginform.username" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item prop="password" label="密码" label-width="50px">
        <el-input type="password" icon="edit" placeholder="请输入密码" v-model="loginform.password" auto-complete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button :loading="logining" type="primary" @click="login()">确 定</el-button>
    </div>
  </el-dialog>
</template>
<script>
  import { Message } from 'element-ui'
  export default {
    data () {
      return {
        dialogVisible: false,
        loginform: {
          username: '',
          password: ''
        },
        loginrule: {
          username: [
            { required: true, message: '请输入账号', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' }
          ]
        },
        logining: false
      }
    },
    methods: {
      resetForm () {
        this.$refs['loginform'].resetFields()
      },
      showDialog () {
        this.dialogVisible = true
        this.resetForm()
      },
      login () {
        let _this = this
        this.$refs.loginform.validate((valid) => {
          if (valid) {
            _this.logining = true
            _this.$store.dispatch('login', _this.loginform)
              .then(() => {
                _this.logining = false
                _this.dialogVisible = false
                Message({
                  message: '登录成功！',
                  type: 'success',
                  duration: 1000
                })
                _this.$emit('onLogin')
                _this.resetForm()
              }).catch(() => {
                _this.logining = false
                Message({
                  message: '登录失败，请稍后重试',
                  type: 'error',
                  duration: 1000
                })
                _this.$nextTick(() => {
                  _this.resetForm()
                })
              })
          }
        })
      }
    }
  }
</script>
