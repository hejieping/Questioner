<template>
  <div class="follow-courses">
    <el-row class="follow course">
      <el-col :span="20">
        <router-link :to="{ path: `/questions/questionType/${course.id}/`}">
          <el-tag> {{ course.subject }} -> {{ course.course }} </el-tag>
        </router-link>
      </el-col>
      <el-col :span="4">
        <el-button :disabled="isSendingFollow" v-loading.lock="isSendingFollow" v-if="showunFollowButton" @click="unFollow()" style="float: right" size="small" type="primary" icon="star-off">取消关注</el-button>
        <el-button :disabled="isSendingFollow" v-loading.lock="isSendingFollow" v-else @click="follow()" style="float: right" size="small" type="primary" icon="star-on">加关注</el-button>
      </el-col>
    </el-row>
  </div>
</template>
<style>

</style>
<script>
  import { getFollowStatus, followQuestionType, unFollowQuestionType } from '@/api/questionType'
  import { Message } from 'element-ui'
  import bus from '../../../assets/eventBus'
  import { mapGetters } from 'vuex'
  export default {
    props: {
      course: Object,
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
        followQuestionType(this.course.id).then((response) => {
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
        getFollowStatus(this.course.id).then((response) => {
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
        unFollowQuestionType(this.course.id).then((response) => {
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
            this.$emit('onRemoveCourse', this.course)
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
