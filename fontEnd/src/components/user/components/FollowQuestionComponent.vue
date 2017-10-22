<template>
  <el-row class="follow">
    <el-col :span="18">
      <div class="question-info">
        <router-link :to="{ path: `/questionDetail/${question.id}/` }">{{ question.questionTitle }}</router-link>
        <div class="questionType-info">
          <el-tag type="gray"> {{ question.questionType.subject }}</el-tag>
          <el-tag> {{ question.questionType.course }} </el-tag>
        </div>
      </div>
    </el-col>
    <el-col :span="6">
      <div style="float: right">
        <el-tag>{{ question.views }} 次浏览</el-tag>
        <span style="margin-left: 10px">
          <el-button :disabled="isSendingFollow" v-loading.lock="isLoadingFollow" v-if="showunFollowButton" @click="unFollow()" style="float: right" size="small" type="primary" icon="star-off">取消关注</el-button>
          <el-button :disabled="isSendingFollow" v-loading.lock="isLoadingFollow" v-else @click="follow()" style="float: right" size="small" type="primary" icon="star-on">加关注</el-button>
        </span>
      </div>
    </el-col>
  </el-row>
</template>
<style>
</style>
<script>
  import { mapGetters } from 'vuex'
  import { hasFollowQuestion, unFollowQuestion, followQuestion } from '@/api/question'
  import { Message } from 'element-ui'
  import bus from '../../../assets/eventBus'
  export default {
    props: {
      question: Object,
      isCurrentUser: Boolean
    },
    data () {
      return {
        hasFollow: false,
        isLoadingFollow: false,
        isSendingFollow: false
      }
    },
    updated: function () {
      this.getFollowStatus()
    },
    mounted: function () {
      bus.$off('loginSuccess')
      const _this = this
      bus.$on('loginSuccess', function () {
        _this.getFollowStatus()
      })
    },
    computed: {
      ...mapGetters(['hasLogin', 'user']),
      showunFollowButton: function () {
        return this.isCurrentUser || this.hasFollow
      }
    },
    methods: {
      follow () {
        if (!this.hasLogin) {
          bus.$emit('requestLogin')
          return
        }
        this.isSendingFollow = true
        followQuestion(this.question.id).then((response) => {
          if (response.status === '200') {
            if (response.result === true) {
              Message({
                message: '关注该用户成功！',
                type: 'success',
                duration: 1000
              })
              this.hasFollow = true
            }
          }
          this.isSendingFollow = false
        }).catch((e) => {
          Message({
            message: '关注该用户失败，请稍后重试！',
            type: 'error',
            duration: 1000
          })
        })
      },
      getFollowStatus () {
        if (this.isCurrentUser || !this.hasLogin) {
          return
        }
        this.isLoadingFollow = true
        hasFollowQuestion(this.question.id).then((response) => {
          this.hasFollow = response.result
          this.isLoadingFollow = false
        }).catch((e) => {
          Message({
            message: '加载信息失败，请稍后重试！',
            type: 'error',
            duration: 1000
          })
        })
      },
      unFollow () {
        if (!this.hasLogin) {
          bus.$emit('requestLogin')
          return
        }
        this.isSendingFollow = true
        unFollowQuestion(this.question.id).then((response) => {
          if (response.status === '200') {
            if (response.result === true) {
              Message({
                message: '取消关注成功！',
                type: 'success',
                duration: 1000
              })
              this.hasFollow = false
            }
          }
          this.isSendingFollow = false
          if (this.isCurrentUser) {
            this.$emit('onRemoveQuestion', this.question)
          }
        }).catch((e) => {
          Message({
            message: '取消关注失败，请稍后重试！',
            type: 'error',
            duration: 1000
          })
        })
      }
    }
  }
</script>
