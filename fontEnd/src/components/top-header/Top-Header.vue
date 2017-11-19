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
      <el-menu-item @click="logout" index="3-3">退出登录</el-menu-item>
    </el-submenu>
    <el-submenu  v-if="hasLogin" id="notification" index="4">
      <template style="" slot="title">
        <el-badge style="margin-right: 20px" :value="noticeNum" :max="99" class="item">
          <i class="fa fa-bell-o fa-lg"></i>
        </el-badge>
      </template>
      <div>
        <el-menu-item v-if="noticeNum <= 0" style="text-align: center; word-wrap: break-word" index="4-1">
          暂时没有收到任何提醒
        </el-menu-item>
        <el-menu-item v-else @click="clearNotice()" index="4-1" style="color: red; text-align: center">
          <i class="el-icon-delete2"></i><span >清空提醒</span>
        </el-menu-item>
        <el-menu-item @click="readAnswer(index, notice)" class="notice" v-for="(notice, index) in notices" index="4-2">
          <span  class="user">{{ notice.answer.account.username }}</span>
          回答了
          <el-tooltip :content="notice.question.questionTitle" placement="top">
          <span  class="questionTitle">
            " {{ notice.question.questionTitle }} "
          </span>
        </el-tooltip>
          <span class="answerTime">
            {{ $moment(notice.answer.answerDateTime).fromNow() }}
          </span>
        </el-menu-item>
      </div>
    </el-submenu>
    <el-menu-item @click="$router.push('/login?register=true')" v-if="!hasLogin" id="register-menu-item" index="5">
      注册
    </el-menu-item>
    <el-menu-item @click="$router.push('/login')"  v-if="!hasLogin" id="login-menu-item" index="6">
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
  #notification ul.el-menu div{
    max-height: 400px;
    overflow: auto;
  }

  #notification .notice .user {
    display: inline-block; max-width: 60px; overflow: hidden
  }
  #notification .notice .questionTitle {
    display: inline-block; max-width: 100px; overflow: hidden
  }
  #notification .notice .answerTime {
    padding-left: 10px; overflow: hidden; display: inline-block; max-width: 100px
  }

  #notification, #personInfo,#register-menu-item,#login-menu-item{
    float: right;
  }
  #notification {
    margin-right: 10px;
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
  import { getUnreadNotice, markNoticeHasRead } from '@/api/questionNotice'
  import { Message } from 'element-ui'
  export default {
    components: { 'login-dialog': LoginDialog },
    data () {
      return {
        question: '',
        notices: [],
        socket: null
      }
    },
    computed: {
      ...mapGetters(['hasLogin', 'user']),
      noticeNum: function () {
        return this.notices.length
      },
      avatar: function () {
        return this.user !== null ? this.user.avatarURL : null
      }
    },
    mounted: function () {
      bus.$off('requestLogin')
      let _this = this
      bus.$on('requestLogin', function () {
        _this.$refs.loginDialog.showDialog()
      })
      this.initAfterLogin()
    },
    methods: {
      initAfterLogin () {
        if (this.hasLogin) {
          this.getUnreadNotice()
          this.connectToSocket()
        }
      },
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
        if (this.socket !== null) {
          this.socket.close()
        }
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
        this.initAfterLogin()
      },
      getUnreadNotice () {
        getUnreadNotice().then((response) => {
          this.notices = response.result
        }).catch((e) => {
          Message({
            message: '不能读取提醒信息，请稍后重试！',
            type: 'error',
            duration: 1000
          })
        })
      },
      clearNotice () {
        for (let i = 0; i < this.notices.length; i++) {
          let notice = this.notices[i]
          markNoticeHasRead(notice.id)
        }
        this.notices = []
      },
      readAnswer (index, notice) {
        markNoticeHasRead(notice.id)
        this.notices.splice(index, 1)
        this.$router.push({ path: `/questionDetail/${notice.question.id}/${notice.answer.id}` })
      },
      connectToSocket () {
        this.socket = new WebSocket('ws://119.23.77.132:8080/websocket/notice?id=' + this.user.id)
        this.socket.onopen = this.socketOpen
        this.socket.onmessage = this.socketReceiveMessage
      },
      socketOpen (event) {
        console.log('WebSocket:已连接')
      },
      socketReceiveMessage (event) {
        this.notices.push(JSON.parse(event.data))
      }
    }
  }
</script>
