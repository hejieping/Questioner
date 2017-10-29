<template>
  <div>

    <!-- 此段必须要引入 -->
    <div id="_umfp" style="display:inline;width:1px;height:1px;overflow:hidden"></div>
    <!-- 表单示例，请替换成您的业务表单 -->
    <div class="container">
      <form action="您提交的服务端地址" method="post">
        <div class="ln">
          <span class="h">用户名：</span>
          <input type="text" name="username">
        </div>

        <div class="ln">
          <span class="h">密码：</span>
          <input type="password" name="password">
        </div>

        <div class="ln">
          <div id="dom_id"></div>
        </div>

        <input type='hidden' id='csessionid' name='csessionid'/>
        <input type='hidden' id='sig' name='sig'/>
        <input type='hidden' id='token' name='token'/>
        <input type='hidden' id='scene' name='scene'/>
        <div class="ln">
          <input type="submit" value="提交">
        </div>
      </form>
    </div>
  </div>
</template>
<style scoped>
  @import '../../static/css/nc.css';
  .container {
    background: #fff;
    padding: 20px;
    margin: 20px;
    width: 400px;
  }
  .ln {
    padding: 5px 0;
  }
  .ln .h {
    display: inline-block;
    width: 4em;
  }
  .ln input {
    border: solid 1px #999;
    padding: 5px 8px;
  }
  body {
    background: #f5f5f5;
    font-size: 14px;
    line-height: 20px;
    margin: 0;
    padding: 0;
  }
  #dom_id .nc_wrapper .nc_scale{
    height: 32px !important;
  }

</style>
<script>
  import '../../static/js/nc'
  /* eslint-disable new-cap,camelcase,no-undef */
  export default {
    components: {
      'remote-js': {
        render (createElement) {
          return createElement('script', { attrs: { type: 'text/javascript', src: this.src } })
        },
        props: {
          src: { type: String, required: true }
        }
      }
    },
    mounted: function () {
      const nc = new noCaptcha()
      const nc_appkey = 'FFFF0000000001787D7C'  // 应用标识,不可更改
      const nc_scene = 'register'  // 场景,不可更改
      const nc_token = [nc_appkey, (new Date()).getTime(), Math.random()].join(':')
      const nc_option = {
        renderTo: '#dom_id', // 渲染到该DOM ID指定的Div位置
        appkey: nc_appkey,
        scene: nc_scene,
        token: nc_token,
        trans: '{"name1":"code100"}', // 测试用，特殊nc_appkey时才生效，正式上线时请务必要删除；code0:通过;code100:点击验证码;code200:图形验证码;code300:恶意请求拦截处理
        callback: function (data) { // 校验成功回调
          console.log(data.csessionid)
          console.log(data.sig)
          console.log(nc_token)

          document.getElementById('csessionid').value = data.csessionid
          document.getElementById('sig').value = data.sig
          document.getElementById('token').value = nc_token
          document.getElementById('scene').value = nc_scene
        }
      }
      nc.init(nc_option)
    }
  }
</script>
