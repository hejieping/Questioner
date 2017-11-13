<template>
  <div id="canvas">
    <div id="nav" v-loading.lock='loading'>
      <ul id="toggle">
        <li>
          <div class="active border">
            <router-link to="/">所有问题</router-link>
          </div>
        </li>
        <li v-for="(value,key) in questionTypeMap">
          <div>
            <a href="javascript:void(0)">
              {{ key }}
            </a>
            <span class="the-btn el-icon-plus"></span>
          </div>
          <ul>
            <li v-for="type in value">
              <router-link  :to="{ path : '/questions/questionType/' + type.id }">
                {{ type.course }}
              </router-link>
            </li>
          </ul>
        </li>
      </ul>
    </div>

    <a href="javascript:void(0)" class="toggle-nav" id="bars"><i class="el-icon-menu"></i></a>

    <div class="question-container">
      <router-view></router-view>
    </div>
  </div>
</template>
<style scoped>
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

  #toggle ul li a.active{
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
  .question-container{
    margin: 0 auto;
    overflow: hidden;
    padding-left: 20px;
    width: 100%;
  }
</style>
<script>
  import $ from 'jquery'
  import { getQuestionType } from '@/api/question'
  import { Message } from 'element-ui'
  import { transformQuestionType2Map } from '@/utils/util'
  import { mapGetters } from 'vuex'

  export default {
    data () {
      return {
        questionTypeArray: [],
        loading: false
      }
    },
    computed: {
      questionTypeMap () {
        return transformQuestionType2Map(this.questionTypeArray)
      },
      ...mapGetters(['questionType'])
    },
    mounted: function () {
      this.initToggleEvent()
      this.openNav()
      this.initQuestionType()
    },
    methods: {
      initEvent () {
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
        $('#toggle ul li a').click(function () {
          $('#toggle ul li a').removeClass('active')
          $(this).addClass('active')
        })
      },
      initToggleEvent () {
        var _this = this
        $('.toggle-nav').click(function () {
          _this.toggleNavigation()
        })
      },
      closeNav () {
        $('#container').removeClass('display-nav')
        $('#canvas').css('width', '100%')
        $('#question-list').css('margin-right', '5%')
      },
      openNav () {
        $('#container').addClass('display-nav')
        $('#canvas').css('width', '70%')
        $('#question-list').css('margin-right', '0')
      },
      toggleNavigation () {
        if ($('#container').hasClass('display-nav')) {
          this.closeNav()
        } else {
          // Open Nav
          this.openNav()
        }
      },
      initQuestionType () {
        if (this.questionType !== null) {
          this.questionTypeArray = this.questionType
          this.$nextTick(function () {
            this.initEvent()
          })
          return
        }
        this.loading = true
        getQuestionType().then((response) => {
          if (response.status === '200') {
            this.questionTypeArray = response.result
            this.loading = false
            this.$store.dispatch('init_question_type', this.questionTypeArray)
            this.$nextTick(function () {
              this.initEvent()
            })
          }
        }).catch((e) => {
          Message({
            message: '获取问题类别信息失败',
            type: 'error',
            duration: 5 * 1000
          })
        })
      }
    }
  }
</script>
