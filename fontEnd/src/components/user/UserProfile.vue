<template>
  <div v-loading.lock="isCheckingUser">
  <div v-if="userIdIsValidate">
  <div class="profile">
    <header class="profile__heading">
      <el-row  style="padding-left: 5%; margin-top: 10px">
        <el-col :span="3">
          <div @click="uploadAvatar()" v-if="isCurrentUser" class="userAvatar uploadImg">
            <el-tooltip  class="item" effect="dark" content="点此上传头像" placement="top">
              <img :src="loginUser.avatarURL">
            </el-tooltip>
            <my-upload field="avatar"
                       @crop-upload-success="cropUploadSuccess"
                       v-model="avatarUploadShow"
                       :width="100"
                       :height="100"
                       url="http://localhost:8080/uploadAvatar"
                       :headers="headers"
                       img-format="png"></my-upload>
          </div>
          <div v-else class="userAvatar">
            <a>
              <img :src="user.avatarURL"/>
            </a>
          </div>
        </el-col>
        <el-col :span="10">
          <div style="margin-left: 20px" class="userInfo">
            <h2>{{ user.username }}</h2>
            <div class="profile__heading--award">
              <a class="profile__rank-btn">
                <span class="h4">{{ user.creditPoint }}</span>
                <span class="profile__rank-btn-text">声望</span>
              </a>
            </div>
            <div class="profile__heading--award-badge">
              <span class="badge">
                <span> <i style="color: gold" class="fa fa-star"></i> </span>
                <span> 0 </span>
              </span>
              <span class="badge">
                <span> <i style="color: grey" class="fa fa-star"></i> </span>
                <span> 0 </span>
              </span>
              <span class="badge">
                <span> <i style="color: saddlebrown" class="fa fa-star"></i> </span>
                <span> 0 </span>
              </span>
            </div>
          </div>
        </el-col>
        <el-col :span="8">
          <div class="profile__heading--desc">
            <div class="profile__heading--desc-heading">
              <span class="profile__heading--desc-heading-dot-warp">
                <span class="profile__heading-dot profile__heading-dot--red"></span>
                <span class="profile__heading-dot profile__heading-dot--yellow"></span>
                <span class="profile__heading-dot profile__heading-dot--green"></span>
              </span>
            </div>
            <div class="profile__heading--desc-body">
              <div class="profile__desc">
                <div style="white-space: pre">
                  该用户太懒什么也没留下.....
                </div>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </header>
    <div class="container-warp">
      <div class="container">
        <el-row style="padding-left: 5%">
          <el-col :span="4">
            <div class="follow-panel">
              <el-button :disabled="isSendingFollow" v-loading.lock="isLoadingFollowUserStatus"  @click="followUser()" v-if="!isCurrentUser && !hasFollow"  size="small" type="success" icon="star-on">加关注</el-button>
              <el-button :disabled="isSendingFollow" v-loading.lock="isLoadingFollowUserStatus" @click="unFollowUser()" v-if="!isCurrentUser && hasFollow" size="small" type="success" icon="star-off">取消关注</el-button>
              <div v-loading.lock="loadingFollowInfo" style="margin-top: 10px" class="follower-panel">
                <el-row style="padding-bottom:15px; border-bottom: solid 1px black">
                  <el-col :span="12" style="border-right: solid 1px #5CB85C">
                    <a @click="myFollow()" href="javascript:void">
                      <span>关注了</span>
                      <span style="display: block"> {{ followed }} 人 </span>
                    </a>
                  </el-col>
                  <el-col :span="12" style="padding-left: 15px">
                    <a @click="myFollower()" href="javascript:void">
                      <span>粉丝</span>
                      <span style="display: block"> {{ followers }} 人 </span>
                    </a>
                  </el-col>
                </el-row>
              </div>
            </div>
            <div class="operation-option">
              <el-menu :defaultActive="activeIndex">
                <el-menu-item @click="$router.push({ path: `/user/${userId}/` })" index="1"><i class="fa fa-home fa-lg"></i>{{ infoPrefix }}主页</el-menu-item>
                <el-menu-item @click="$router.push({ path: `/user/${userId}/userQuestion` })"  index="2"><span v-loading.lock="isGettingQuestionCount"><i class="fa fa-question-circle fa-lg"></i>{{ infoPrefix }}提问 {{ questionCount }}</span> </el-menu-item>
                <el-menu-item @click="$router.push({ path: `/user/${userId}/userAnswer` })" index="3"><span v-loading.lock="isGettingAnswerCount"><i class="fa fa-key fa-lg"></i>{{ infoPrefix }}回答 {{ answerCount }}</span></el-menu-item>
                <el-menu-item @click="$router.push({ path: `/user/${userId}/userActivity` })" index="4"><i class="fa fa-line-chart fa-lg"></i>{{ infoPrefix }}动态 </el-menu-item>
                <el-menu-item @click="$router.push({ path: `/user/${userId}/reputation` })" index="5"><i class="fa fa-star fa-lg"></i>声望记录</el-menu-item>
              </el-menu>
            </div>
          </el-col>
          <el-col :span="19" style="padding-left: 20px">
            <router-view></router-view>
          </el-col>
        </el-row>
        <div style="margin-bottom: 100px">

        </div>
      </div>
    </div>
  </div>
  </div>
  <div v-if="userIdIsValidate === false">
    <div style="margin: 100px 50px">
      <el-alert
        title="找不到该用户"
        type="error"
        description="该用户不存在或已被永久注销" :closable="false">
      </el-alert>
    </div>
  </div>
  </div>
</template>
<style>
  h2,h3 {
    font-weight: 500;
  }
  .pagination {
    float: right;
    margin-top: 10px;
  }
  .follow {
    padding: 12px 5px;
    border-bottom: solid 1px #666666;
  }
</style>
<style scoped>
  .profile__heading {
    background-color: #f6f6f6;
    padding: 40px 0 0 0;
  }
  .profile__heading h2 {
    font-weight: 500;
  }
  .profile__rank-btn {
    margin-top: 5px;
    background: #DFF0D8;
    border: 1px solid #5CB85C;
    border-radius: 100px;
    font-size: 15px;
    color: #43894E;
    padding: 4px 12px;
    line-height: 1;
    display: inline-block;
  }
  .profile__rank-btn-text {
    font-size: 14px;
    margin-left: 1px;
  }
  .profile__heading--award {
    display: inline-block;
  }
  .profile__heading--award-badge {
    display: inline-block;
    margin-left: 15px;
    line-height: 1;
    color: #333;
    vertical-align: middle;
  }
  .profile__heading--award-badge .badge {
    margin-left: 10px;
  }
  .profile__heading--desc-heading {
    height: 32px;
    background-color: #E3E3E3;
    padding: 0 12px;
    border-top-left-radius: 3px;
    border-top-right-radius: 3px;
  }
  .profile__heading--desc-heading-dot-warp {
    height: 32px;
  }
  .profile__heading-dot {
    display: inline-block;
    width: 12px;
    height: 12px;
    border-radius: 50%;
    background-color: #eee;
    margin-right: 8px;
  }
  .profile__heading-dot--red {
    background-color: #FF5F57;
  }
  .profile__heading-dot--yellow {
    background-color: #FFBD2E;
  }
  .profile__heading-dot--green {
    background-color: #28CA42;
  }
  .profile__heading--desc-body {
    background-color: #EEEEEE;
    width: 100%;
    font-size: 13px;
    color: #666666;
    overflow: auto;
    height: 150px;
    padding: 20px;
    font-family: "Source Code Pro", Consolas, Menlo, Monaco, "Courier New", monospace;
  }
  .container-warp {
    margin-top: 30px;
  }
  .follower-panel a {
    color: black;
  }
  .follower-panel a:hover {
    color: grey;
  }

  .container .operation-option .el-menu-item i {
    margin-right: 5px;
  }
  .userAvatar img {
    width: 100%;
    border-radius: 50%;
    background: white;
    vertical-align: middle
  }
  .uploadImg {
    cursor: pointer;
  }
</style>
<script>
  import { getUser, getFollowInfo, hasFollowUser, followUser, unFollowUser } from '@/api/user'
  import { getUserQuestionCount } from '@/api/question'
  import { getUserAnswerCount } from '@/api/answer'
  import { Message } from 'element-ui'
  import { mapGetters } from 'vuex'
  import myUpload from 'vue-image-crop-upload'
  import bus from '../../assets/eventBus'
  export default {
    components: { 'my-upload': myUpload },
    data () {
      return {
        userId: '',
        userIdIsValidate: '',
        isCheckingUser: false,
        user: null,
        loadingFollowInfo: false,
        followers: 0,
        followed: 0,
        activeIndex: '1',
        avatarUploadShow: false,
        headers: null,
        hasFollow: false,
        isSendingFollow: false,
        isLoadingFollowUserStatus: false,
        isGettingAnswerCount: false,
        isGettingQuestionCount: false,
        answerCount: null,
        questionCount: null
      }
    },
    mounted () {
      this.updateUserInfo()
      const _this = this
      bus.$off('loginSuccess')
      bus.$on('loginSuccess', function () {
        _this.updateFollowUserStatus()
      })
    },
    computed: {
      ...mapGetters({loginUser: 'user'}),
      ...mapGetters(['token', 'hasLogin']),
      isCurrentUser: function () {
        return this.loginUser !== null && parseInt(this.loginUser.id) === parseInt(this.userId)
      },
      infoPrefix: function () {
        return this.isCurrentUser ? '我的' : '他的'
      }
    },
    methods: {
      updateUserInfo () {
        this.userId = this.$route.params.userId
        this.isCheckingUser = true
        this.loadingFollowInfo = true
        this.getUserInfo()
        this.getFollowInfo()
        this.updateFollowUserStatus()
        this.getQuestionAnswerCount()
      },
      getUserInfo () {
        getUser(this.userId).then((response) => {
          this.userIdIsValidate = response.status === '200'
          this.isCheckingUser = false
          this.user = response.result
        }).catch((e) => {
          Message({
            message: '请求用户的信息出错，请稍后再试！',
            type: 'error',
            duration: 1000
          })
        })
      },
      getFollowInfo () {
        getFollowInfo(this.userId).then((response) => {
          this.loadingFollowInfo = false
          this.followers = response.result.followerCount
          this.followed = response.result.followedCount
        }).catch((e) => {
          Message({
            message: '获取用户的关注信息失败，请稍后重试！',
            type: 'error',
            duration: 1000
          })
        })
      },
      getQuestionAnswerCount () {
        this.isGettingAnswerCount = true
        getUserAnswerCount(this.userId).then((response) => {
          this.isGettingAnswerCount = false
          this.answerCount = response.result
        }).catch((e) => {
          Message({
            message: '获取信息失败，请稍后重试！',
            type: 'error',
            duration: 1000
          })
        })
        this.isGettingQuestionCount = true
        getUserQuestionCount(this.userId).then((response) => {
          this.isGettingQuestionCount = false
          this.questionCount = response.result
        }).catch((e) => {
          Message({
            message: '获取信息失败，请稍后重试！',
            type: 'error',
            duration: 1000
          })
        })
      },
      uploadAvatar () {
        let token = this.token
        this.headers = {
          Authorization: token
        }
        this.avatarUploadShow = true
      },
      cropUploadSuccess (response, field) {
        this.$store.dispatch('changeAvatar', response.result)
      },
      followUser () {
        if (!this.hasLogin) {
          bus.$emit('requestLogin')
          return
        }
        this.isSendingFollow = true
        const _this = this
        followUser(this.userId).then((response) => {
          if (response.status === '200') {
            if (response.result === true) {
              _this.hasFollow = true
              Message({
                message: '关注用户成功！',
                type: 'success',
                duration: 1000
              })
            }
          }
          _this.isSendingFollow = false
        }).catch((e) => {
          Message({
            message: '关注用户失败，请稍后重试！',
            type: 'error',
            duration: 1000
          })
        })
      },
      unFollowUser () {
        if (!this.hasLogin) {
          bus.$emit('requestLogin')
          return
        }
        this.isSendingFollow = true
        const _this = this
        unFollowUser(this.userId).then((response) => {
          if (response.status === '200') {
            if (response.result === true) {
              _this.hasFollow = false
              Message({
                message: '取消关注成功',
                type: 'success',
                duration: 1000
              })
            }
          }
          _this.isSendingFollow = false
        }).catch((e) => {
          Message({
            message: '取消关注失败，请稍后重试！',
            type: 'error',
            duration: 1000
          })
        })
      },
      updateFollowUserStatus () {
        if (this.isCurrentUser || !this.hasLogin) {
          return
        }
        this.isLoadingFollowUserStatus = true
        hasFollowUser(this.userId).then((response) => {
          this.isLoadingFollowUserStatus = false
          this.hasFollow = response.result
        }).catch((e) => {
          Message({
            message: '加载数据失败，请稍后重试！',
            type: 'error',
            duration: 1000
          })
        })
      },
      myFollow () {
        let aimPath = '/user/' + this.userId + '/myFollow'
        this.$router.push({ path: aimPath })
        this.getFollowInfo()
      },
      myFollower () {
        let aimPath = '/user/' + this.userId + '/followers'
        this.$router.push({ path: aimPath })
        this.getFollowInfo()
      }
    },
    beforeRouteUpdate (to, from, next) {
      this.activeIndex = to.meta.index
      next()
    },
    watch: {
      '$route' (to, from) {
        if (from.params.userId !== to.params.userId) {
          this.updateUserInfo()
        }
      }
    }
  }
</script>
