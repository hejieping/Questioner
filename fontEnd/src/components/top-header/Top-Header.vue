<template>
  <el-menu theme="dark" class="el-menu-demo" mode="horizontal" @select="handleSelect">
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
    <el-menu-item @click="$router.push('/editQuestion')" index="2">
        去提问
    </el-menu-item>
    <el-submenu id="personInfo" index="3">
      <template slot="title"><span><img src="./img/user.jpg" class="thumbnail">
        个人中心</span>
      </template>
      <el-menu-item @click="$router.push('/personInfo')" index="3-1">
          个人资料
       </el-menu-item>
      <el-menu-item index="3-2">
        <router-link to="/personInfo">
          问题中心
        </router-link>
      </el-menu-item>
      <el-menu-item @click="logout" index="3-3">退出登录</el-menu-item>
    </el-submenu>
  </el-menu>
</template>
<style scoped>
  ul.el-menu{
    padding-left: 40px;
  }
  #personInfo{
    float: right;
    margin-right: 65px;
  }
</style>
<script>
  export default {
    data () {
      return {
        message: 'HelloWorld',
        question: ''
      }
    },
    methods: {
      handleSelect (key, keyPath) {
        console.log(key, keyPath)
      },
      search () {
        alert(this.question)
      },
      logout () {
        this.$store.dispatch('logout')
          .then(() => {
            this.$router.push({ path: '/login' })
          })
      }
    }
  }
</script>
