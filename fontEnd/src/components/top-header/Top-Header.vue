<template>
  <div>
  <el-menu theme="dark" class="el-menu-demo" mode="horizontal">
    <el-menu-item index="0">
      <router-link to="/">
        <img width="61" height="30" src="./img/logo.png" />
      </router-link>
    </el-menu-item>
    <el-menu-item index="1" >
      <el-input
        @keyup.enter.native="search" v-model="question" icon="search" :on-icon-click="search" placeholder="请输入搜索内容">
      </el-input>
    </el-menu-item>
    <el-menu-item @click="postQuestion()" index="2">
        去提问
    </el-menu-item>
    <el-submenu v-if="hasLogin" id="personInfo" index="3">
      <template slot="title"><span><img :src="user.avatarURL" class="top-header-img">
        个人中心</span>
      </template>
      <el-menu-item @click="$router.push({ path: `/user/${user.id}` })" index="3-1">
          我的主页
       </el-menu-item>
      <el-menu-item @click="$router.push('/showContent')" index="3-2">
          问题中心
      </el-menu-item>
      <el-menu-item @click="logout" index="3-3">退出登录</el-menu-item>
    </el-submenu>

    <el-menu-item @click="$router.push('/login?register=true')" v-if="!hasLogin" id="register-menu-item" index="4">
      注册
    </el-menu-item>
    <el-menu-item @click="$router.push('/login')"  v-if="!hasLogin" id="login-menu-item" index="5">
      登录
    </el-menu-item>
  </el-menu>
    <login-dialog  ref="loginDialog" @onLogin="loginSuccess"></login-dialog>
  </div>
</template>
<style scoped>
  ul.el-menu{
    padding-left: 40px;
  }
  #personInfo,#register-menu-item,#login-menu-item{
    float: right;
  }
  #personInfo, #register-menu-item{
    margin-right: 65px;
  }
  .top-header-img{
    width: 30px;
    height: 30px;
    border-radius: 50%;
    vertical-align: middle;
  }
</style>
<script>
  import bus from '../../assets/eventBus.js'
  import store from '@/store'
  import LoginDialog from '../common/LoginDialog.vue'
  import { mapGetters } from 'vuex'
  export default {
    components: { 'login-dialog': LoginDialog },
    data () {
      return {
        question: ''
      }
    },
    computed: {
      ...mapGetters(['hasLogin', 'user'])
    },
    mounted: function () {
      bus.$off('requestLogin')
      let _this = this
      bus.$on('requestLogin', function () {
        _this.$refs.loginDialog.showDialog()
      })
    },
    methods: {
      search () {
        if (store.getters.isQuestionListPage) {
          bus.$emit('searchQuestion', this.question)
        } else {
          let path = '/questions/searchQuestion'
          if (this.question !== '') {
            path = path + '/' + this.question
          }
          this.$router.push(path)
        }
      },
      logout () {
        this.$store.dispatch('logout')
          .then(() => {
            this.$router.push({ path: '/login' })
          })
      },
      postQuestion () {
        if (this.hasLogin) {
          this.$router.push('/editQuestion')
        } else {
          this.$refs.loginDialog.showDialog()
        }
      },
      loginSuccess () {
        bus.$emit('loginSuccess')
      }
    }
  }
</script>
