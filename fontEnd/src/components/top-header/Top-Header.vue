<template>
  <header>
    <div class="headerNav">
      <img width="61" height="30" src="./img/logo.png" />
      <form class="search">
        <input type="text" placeholder="搜索问题..." v-model="searchKeyWord" @keyup.enter="searchQuestion"/>
        <a class="searchBtn" @click="searchQuestion">
          <i @click="searchQuestion"></i>
        </a>
      </form>

      <ul class="nav">
        <li class="current">
          <a :href="navList[0].link">
            {{ navList[0].navName }}
          </a>
        </li>
        <li v-for="nav in navList.slice(1)">
          <a :href="nav.link">
            {{nav.navName}}
          </a>
        </li>
      </ul>

      <a class="askBtn" href="">提问</a>

      <div class="userInfo">
        <img src="./img/user.jpg" class="thumbnail"/>
        <a href="">YuXia</a>
        <ul>
          <li v-for="option in options">
            <a :href="option.link">
              <b>{{option.icon}}</b>
              {{option.optName}}
            </a>
          </li>
          <li @click="logout">
            <a>
              <b>4</b>
              退出登录
            </a>
          </li>
        </ul>
      </div>
    </div>
  </header>
</template>

<style scoped>
  header{
    background:linear-gradient(to bottom,#2d84db,#044e97);
    color:#eee;
    width:100%;
    min-width:960px;
  }
  a{
    font-size: 14px;
    color:#eee;
    padding:12px 0;
  }
  .headerNav{
    width:960px;
    margin:0 auto;
    display:flex;
    justify-content:space-around;
    align-items: center;
  }
  .search{
    flex:4;
  }
  .search input{
    padding:7px 10px;
    color: #49525c;
    font-size: 14px;
    line-height: 17px;
    background-color: #e1eaf2;
    border: 1px solid #045bb2;
    border-radius: 4px;
    box-shadow: inset 0 1px 3px rgba(0,0,0,.2), 0 1px 0 rgba(255,255,255,.1);
    margin-left:15px;
    width:330px;
    height:33px;
    outline:none;
  }
  .search input:focus{
    background:#f3f3f3;
  }
  .searchBtn{
    display:inline-block;
    width:40px;
    height:33px;
    border: 1px solid #045bb2;
    border-radius: 4px;
    box-shadow:0 1px 0 rgba(255,255,255,.1);
    background:#0E77E5;
    position:absolute;
  }
  .searchBtn i{
    display:inline-block;
    width:15px;
    height:15px;
    background: url('../../assets/img/sprite-icons-2.png') no-repeat -82px 0;
    position: relative;
    top: -5px;
    left: 12px;
  }
  .nav{
    flex:3;
    padding-left:46px;
  }
  .nav li{
    display:inline-block;
    width:55px;
    text-align:center;
    height:45px;
    line-height: 45px;
  }
  .nav li.current{
    background: #065FB6;
    box-shadow: inset 1px 1px 15px #0a4191;
  }
  .askBtn{
    width: 66px;
    height: 32px;
    line-height:7px;
    border: 1px solid #045bb2;
    border-radius: 4px;
    background: #0e78e7;
    background: linear-gradient(to bottom,#0e7bef,#0d73da);
    box-shadow: 0 1px 2px rgba(0,0,0,.05), inset 0 1px 1px rgba(255,255,255,.15);
    color: #fff;
    font-size: 14px;
    text-align: center;
    text-shadow: 0 1px 1px rgba(0,0,0,.12);
    outline: 0;
    cursor: pointer;
    transition: all .2s ease-in-out;
  }
  .userInfo{
    position:relative;
    width:120px;
    text-align: center;
    line-height: 45px;
  }
  .userInfo .thumbnail{
    display:inline-block;
    width:30px;
    height:30px;
    margin-top:7px;
    border:1px solid #dcdcdc;
    border-radius:3px;
    vertical-align: top;
  }
  .userInfo>a{
    color:#fff;
    margin-left:8px;
  }
  .userInfo ul{
    width:145px;
    line-height:1;
    position:absolute;
    top:45px;
    left:0;
    background:#044e97;
    display:none;
  }
  .userInfo:hover ul{
    display:block;
  }
  .userInfo ul li a{
    display: block;
    width: 145px;
    height: 40px;
    color: #fff;
    border-top: 1px solid #0d5aa5;
    box-shadow: 0 1px 1px rgba(0,0,0,.4);
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
    cursor: pointer;
    padding-left:15px;
    text-align:left;
  }
  .userInfo ul li a:hover{
    background:#045bb2;
    text-decoration: none;
  }
</style>

<script>
  import bus from '../../assets/eventBus'

  export default {
    data () {
      return {
        navList: [
          {
            navName: '首页', link: ''
          }
        ],
        options: [
          {optName: '我的主页', link: '', icon: '1'},
          {optName: '私信', link: '', icon: '2'},
          {optName: '设置', link: '', icon: '3'}
        ],
        searchKeyWord: ''
      }
    },
    methods: {
      searchQuestion () {
        if (this.searchKeyWord !== '') {
          bus.$emit('search', this.searchKeyWord)
          this.searchKeyWords = ''
        }
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
