<template>
  <div id="loginContainer">
    <el-card class="box-card">
      <div slot="header" class="clearfix title">
        <span style="line-height: 36px;">遇见知识</span>
      </div>
      <div>
        <el-form ref="loginform" :model="loginform" :rules="loginrule">
          <el-form-item prop="username">
            <el-input icon="edit" type="text" v-model="loginform.username" autoComplete="false"
                      autofocus placeholder="请输入账号" ></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input type="password" v-model="loginform.password" autoComplete="false"
                   icon="edit" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button  style="width: 100%" type="success" :loading="logining" @click.native.prevent="login" >登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    <span v-show="hasLogin">sderer</span>
  </div>
</template>
<script>
  import store from '@/store'
  export default {
    data () {
      return {
        loginform: {
          username: '',
          password: ''
        },
        loginrule: {
          account: [
            { required: true, message: '请输入账号', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' }
          ]
        },
        logining: false
      }
    },
    computed: {
      hasLogin () {
        return store.Login
      }
    },
    methods: {
      login (ev) {
        let _this = this
        this.$refs.loginform.validate((valid) => {
          if (valid) {
            _this.logining = true
            _this.$store.dispatch('login', _this.loginform)
              .then(() => {
                _this.logining = false
                _this.$router.push({ path: '/' })
              }).catch(() => {
                _this.logining = false
              })
          }
        })
      }
    }
  }
</script>
<style scoped>
  #loginContainer{
    margin: 100px auto;
    width: 400px;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }
  .title{
    font-family: "Arial","Microsoft YaHei","黑体","宋体",sans-serif;
    color: dodgerblue;
    font-size: 2em;
  }
</style>
