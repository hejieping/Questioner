<template>
  <div id="container">
    <top-header></top-header>
    <div id="canvas">
      <div id="nav">
        <ul id="toggle">
          <li>
            <div class="active border">
              <a href="">Home</a>
            </div>
          </li>
          <li>
            <div>
              <a href="#">About Us</a>
              <span class="the-btn el-icon-plus"></span>
            </div>
            <ul style="">
              <li>
                <a href="#">OUR TEAM</a>
              </li>
              <li>
                <a href="#">OUR SERVICES</a>
              </li>
            </ul>
          </li>
          <li>
            <div style="word-wrap: break-word">
              <a href="#">PORTFOLIO</a>
              <span class="the-btn el-icon-plus"></span>
            </div>
            <ul>
              <li>
                <a href="#">WEB DESIGN</a>
              </li>
              <li>
                <a href="#">GRAPHIC DESIGN efr</a>
              </li>
            </ul>
          </li>
          <li>
            <div>
              <a href="#">CONTACT</a>
            </div>
          </li>
        </ul>
      </div>

      <a href="#" class="toggle-nav" id="bars"><i class="el-icon-menu"></i></a>

      <div class="htmleaf-container">
        <div>DEKOe</div>
        <div>DEKOe</div>
        <div>DEKOe</div>
        <div>DEKOe</div>
        <div>DEKOe</div>
        <div>DEKOe</div>
        <div>DEKOe</div>
        <div>DEKOe</div>
        <div>DEKOe</div>
        <div>DEKOe</div>
        <div>DEKOe</div>
        <div>DEKOe</div>
        <div>DEKOe</div>
        <div>DEKOe</div>
        <div>DEKOe</div>
        <div>DEKOe</div>
        <div>DEKOe</div>
        <div>DEKOe</div>
        <div>DEKOe</div>
        <div>DEKOe</div>
        <div>DEKOe</div>
        <div>DEKOe</div>
        <div>DEKOe</div>
        <div>DEKOe</div>
        <div>DEKOe</div>
        <div>DEKOe</div>
      </div>
    </div>
  </div>
</template>
<style scoped>
  h1,h2,h3{text-align: center;}
  ol, ul {
    list-style: none;
  }
  #container{
    width: 100%;
    min-height: 100vh;
    position: relative;
    overflow: auto;
  }
  #canvas{
    width: 100%;
    height: 100%;
    padding: 20px 0;
    position: relative;

    -webkit-transform:translateX(0);
    -moz-transform:translateX(0);
    -ms-transform:translateX(0);
    -o-transform:translateX(0);
    transform:translateX(0);

    -webkit-transition:.5s ease all;
    -moz-transition:.5s ease all;
    -o-transition:.5s ease all;
    transition:.5s ease all;
  }
  #nav{
    width: 30%;
    min-height: 100vh;
    height: 100%;
    background: #38935f;
    position: absolute;
    left: -30%;
    top:0;

    -webkit-transition:.5s ease all;
    -moz-transition:.5s ease all;
    -o-transition:.5s ease all;
    transition:.5s ease all;

    /* By default, rotate the menu 90deg inwards */
    -webkit-transform:rotateY(-90deg);
    -moz-transform:rotateY(-90deg);
    -ms-transform:rotateY(-90deg);
    -o-transform:rotateY(-90deg);
    transform:rotateY(-90deg);
  }
  #toggle {
    list-style: none;
    margin-top: 40px;
  }
  #toggle div:hover {
    background: rgba(0,0,0,0.2);
    -webkit-box-shadow:inset 0 -1px rgba(0,0,0,0);
    -moz-box-shadow:inset 0 -1px rgba(0,0,0,0);
    box-shadow:inset 0 -1px rgba(0,0,0,0);
    color: #fff;
  }
  #toggle div.active {
    background: #1f9d55;
  }
  #toggle div {

    cursor: pointer;
    display: block;
    border-bottom: 1px solid rgba(0,0,0,0.2);
  }
  span.the-btn{
    float: right;
    font-size: 20px;
    height: 30px;
    width: 43px;
    margin-top: 10px;
    margin-right: 8px;
    padding:0;
    color: #fff;
  }
  #toggle ul {
    list-style: disc;
    display: none;
    color: #fff;
    background: rgba(0,0,0,0.2);
    -webkit-box-shadow:inset 0 -1px rgba(0,0,0,0);
    -moz-box-shadow:inset 0 -1px rgba(0,0,0,0);
    box-shadow:inset 0 -1px rgba(0,0,0,0);

  }

  #toggle li a {
    line-height: 41px;
    color: #fff;
    list-style: circle;
    width: 240px;
    padding: 0;
    margin: 0 0 0 50px;

  }
  #toggle a {
    margin: 0 0 0 47px;
    padding: 0;
    font-family: 'Lato';
    color: #fff;
    line-height: 41px;
    font-weight: normal;
    font-size: 18px;
    text-decoration: none;

  }

  #toggle ul li {
    margin-left: 60px;
  }

  #toggle ul li a:hover{
    background: #1f9d55;
  }

  #toggle ul li a {
    margin-left: 0;
  }
  #container.display-nav #canvas{
    -webkit-transform:translateX(30%);
    -moz-transform:translateX(30%);
    -ms-transform:translateX(30%);
    -o-transform:translateX(30%);
    transform:translateX(30%);
  }
  #container.display-nav #nav{
    -webkit-transform-origin:100% 50%;
    -moz-transform-origin:100% 50%;
    -ms-transform-origin:100% 50%;
    -o-transform-origin:100% 50%;
    transform-origin:100% 50%;

    -webkit-transform:perspective(600px) rotateY(0deg);
    -moz-transform:perspective(600px) rotateY(0deg);
    -ms-transform:perspective(600px) rotateY(0deg);
    -o-transform:perspective(600px) rotateY(0deg);
    transform:perspective(600px) rotateY(0deg);
  }
  #bars i{
    font-size: 20px;
    margin-left: 20px;
    color: #38935f;
  }
  #bars i:hover{
    color: #48b978
  }
  .htmleaf-container{
    margin: 0 auto;
    text-align: center;
    overflow: auto;
  }
</style>
<script>
  import $ from 'jquery'
  import TopHeader from './top-header/Top-Header.vue'

  export default {
    data () {
      return {
        message: 'Hello World'
      }
    },
    components: {
      'top-header': TopHeader
    },
    mounted: function () {
      this.initEvent()
    },
    methods: {
      initEvent () {
        var _this = this
        $('.toggle-nav').click(function () {
          _this.toggleNavigation()
        })
        // SLiding codes
        $('#toggle > li > div').click(function () {
          if ($(this).next().is(':visible') === false) {
            $('#toggle ul').slideUp()
          }

          let $currIcon = $(this).find('span.the-btn')

          $('span.the-btn').not($currIcon).addClass('el-icon-plus').removeClass('el-icon-minus')

          $currIcon.toggleClass('el-icon-plus el-icon-minus')

          $(this).next().slideToggle()

          $('#toggle > li > div').removeClass('active')
          $(this).addClass('active')
        })
      },

      toggleNavigation () {
        if ($('#container').hasClass('display-nav')) {
          // Close Nav
          $('#container').removeClass('display-nav')
          $('#canvas').css('width', '100%')
        } else {
          // Open Nav
          $('#container').addClass('display-nav')
          $('#canvas').css('width', '70%')
        }
      }
    }
  }
</script>
