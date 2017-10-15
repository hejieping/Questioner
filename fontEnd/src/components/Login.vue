<template>
  <div id = "wrap-banner">
  <div id="loginContainer">
    <el-card class="box-card">
      <div slot="header" class="clearfix login-title">
        <span style="">遇见知识</span>
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
          <el-form-item class="login-button">
            <el-button style="width: 100%" type="success" :loading="logining" @click.native.prevent="login" >登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
  </div>
    <vue-particles
      color="#dedede"
      :particleOpacity="0.7"
      :particlesNumber="80"
      shapeType="circle"
      :particleSize="4"
      linesColor="#dedede"
      :linesWidth="1"
      :lineLinked="true"
      :lineOpacity="0.4"
      :linesDistance="150"
      :moveSpeed="3"
      :hoverEffect="true"
      hoverMode="grab"
      :clickEffect="true"
      clickMode="push">
    </vue-particles>
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
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin: 100px auto;
    width: 400px;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
    z-index: 1;
  }
  #loginContainer button span{
    color: white;
    font-size: 20px;
    line-height: 1;
  }
  .login-title{
    text-align: center;
  }
  #loginContainer .login-title span{
    font-family: "Arial","Microsoft YaHei","黑体","宋体",sans-serif;
    color: dodgerblue ;
    font-size: 2em ;
    line-height: 36px;
  }

  #wrap-banner {
    position: relative;
    -webkit-align-items: center;
    -ms-flex-align: center;
    align-items: center;
    display: -webkit-flex;
    display: flex;
    height: 100%;
    justify-content: center;
  }

  #particles-js {
    background-image: url("../assets/sky.jpg");
    background-size: cover;
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 98%;
  }
</style>
