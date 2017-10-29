<template>
  <div class="back-to-top" @click="backToTop" v-show="showReturnToTop">
      <i style="font-size: 32px" :class="[bttOption.iClass]" :style="{color: bttOption.iColor}"></i>
  </div>
</template>

<script>
  import { scrollIt } from '../../utils/scrollIt'
  export default {
    name: 'back-to-top',
    props: {
      iClass: {
        type: String,
        default: 'el-icon-caret-top'
      },
      iColor: {
        type: String,
        default: '#fff'
      },
      iFontSize: {
        type: String,
        default: '32px'
      },
      pageY: {
        type: Number,
        default: 400
      },
      transitionName: {
        type: String,
        default: 'linear'
      }
    },
    data: function () {
      return {
        showTooltips: false,
        showReturnToTop: false
      }
    },
    computed: {
      bttOption: function () {
        return {
          iClass: this.iClass,
          iColor: this.iColor,
          iFontSize: this.iFontSize
        }
      }
    },
    methods: {
      currentPageYOffset () {
        window.pageYOffset > this.pageY ? this.showReturnToTop = true : this.showReturnToTop = false
      },
      backToTop () {
        scrollIt(0, 1000, this.transitionName, this.currentPageYOffset)
      }
    },
    created () {
      window.addEventListener('scroll', this.currentPageYOffset)
    },
    beforeDestroy () {
      window.removeEventListener('scroll', this.currentPageYOffset)
    }
  }
</script>
<style scoped>
  .back-to-top {
    position: fixed;
    bottom: 5%;
    right: 20px;
    z-index: 9999;
    cursor: pointer;
    width: auto;
    background-color: #58b7ff;
    border-radius: 50%;
  }
  .back-to-top:hover{
    background-color: blue;
  }
  .back-to-top i {
    font-size: 32px;
    display: inline-block;
    position: relative;
    text-align: center;
    padding: 5px;
    background-color: rgba(234, 231, 231, 0.52);
    border-radius: 5px;
    transition: all 0.3s linear;
  }
</style>
