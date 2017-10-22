<template>
  <el-row class="follow">
    <el-col :span="20">
      <img class="follower-img" :src="follower.avatarURL"/>
      <div class="followers-info">
        <router-link :to="{ path: `/user/${follower.id}/` }">{{ follower.username }}</router-link>
        <div class="follower-reputation">
          <span> {{ follower.creditPoint }} </span>
          <span>声望</span>
        </div>
      </div>
    </el-col>
    <el-col :span="4">
      <div v-if="!hideButton">
        <el-button :disabled="isSendingFollow" v-loading.lock="isSendingFollow" v-if="showunFollowButton" @click="unFollow()" style="float: right" size="small" type="primary" icon="star-off">取消关注</el-button>
        <el-button :disabled="isSendingFollow" v-loading.lock="isSendingFollow" v-else @click="follow()" style="float: right" size="small" type="primary" icon="star-on">加关注</el-button>
      </div>
    </el-col>
  </el-row>
</template>
<style>
  .follower-img {
    width: 38px;
    height: 38px;
    border-radius: 50%;
    float: left;
    margin-right: 10px;
  }
  .follower-info {
    padding-left: 20px;
  }
  .follower-reputation {
    font-size: 14px;
    color: #999999;
    line-height: 20px;
  }
</style>
<script>
  import { mapGetters } from 'vuex'
  import { hasFollowUser, followUser, unFollowUser } from '@/api/user'
  import { Message } from 'element-ui'
  import bus from '../../../assets/eventBus'
  export default {
    props: {
      follower: Object,
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
      },
      hideButton: function () {
        return this.user !== null && this.user.id === this.follower.id
      }
    },
    methods: {
      follow () {
        if (!this.hasLogin) {
          bus.$emit('requestLogin')
          return
        }
        this.isSendingFollow = true
        followUser(this.follower.id).then((response) => {
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
        hasFollowUser(this.follower.id).then((response) => {
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
        unFollowUser(this.follower.id).then((response) => {
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
            this.$emit('onRemoveUser', this.follower)
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
