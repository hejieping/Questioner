<template>
  <div id="index">
    <top-header></top-header>
    <div class="content-body">
      <div class="content">
        <div class="home-option">
          <span class="latest">
            <i class="listIcon"></i>
            <a href="">最新动态</a>
          </span>
          <span class="config">
            <i class="gearIcon"></i>
            <a href="">设置</a>
          </span>
        </div>
        <main-content></main-content>
      </div>
      <div class="aside">
      </div>
    </div>
  </div>
</template>
<style scoped>
  /* 内容栏 边栏 布局 */
  .content-body{
    display:flex;
    justify-content:flex-start;
  }
  .content{
    flex:6;
    padding:0 50px;
    min-width:780px;
  }
  .aside{
    flex:2;
    min-width:260px;
  }

  /* 首页操作栏 */
  .home-option{
    margin:40px 0 15px 0;
    display:flex;
    justify-content: space-between;
  }
  .latest{
    font-size: 14px;
    color: #666;
    font-weight: 700;
  }
  .config{
    color: #999;
  }
  .home-option i{
    display:inline-block;
    width:16px;
    height:16px;
    position:relative;
    top:4px;
    left:4px;
  }
  .listIcon{
    background:url('../assets/img/sprites-1.9.2.4c54885a.png') no-repeat -71px -88px;
  }
  .gearIcon{
    background:url('../assets/img/sprites-1.9.2.4c54885a.png') no-repeat -88px -148px;
  }
</style>
<script>
  import TopHeader from './top-header/Top-Header.vue'
  import MainContent from './main-content/Content.vue'
  export default{
    components: {
      'top-header': TopHeader,
      'main-content': MainContent
    }
  }
</script>
