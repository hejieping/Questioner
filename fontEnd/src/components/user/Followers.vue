<template>
  <div v-loading.lock="isLoadingFollowers">
    <h3 class="follower_heading">
      <span>{{ textPrefix }}粉丝</span>
    </h3>
    <hr style="solid-color: black 1px">
    <div class="followers-panel">
      <div v-for="follower in followers">
        <follower :follower="follower"></follower>
      </div>
      <div v-show="showPagination" class="block pagination">
        <el-pagination
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :page-size="pageSize"
          layout="prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>
    </div>
  </div>
</template>
<style scoped>

  .follower_heading {
    padding-bottom: 20px;
    border-bottom: solid 1px grey;
  }

</style>
<script>
  import { getFollowersByPage } from '@/api/user'
  import { Message } from 'element-ui'
  import Follower from './components/FollowerComponent.vue'
  import { mapGetters } from 'vuex'
  export default {
    components: { 'follower': Follower },
    data () {
      return {
        total: 0,
        pageSize: 10,
        currentPage: 1,
        isLoadingFollowers: true,
        userId: null,
        followers: []
      }
    },
    mounted () {
      this.userId = this.$route.params.userId
      this.getFollowers()
    },
    computed: {
      showPagination () {
        return this.total > this.pageSize
      },
      ...mapGetters(['user']),
      isCurrentUser: function () {
        return this.user !== null && parseInt(this.user.id) === parseInt(this.userId)
      },
      textPrefix: function () {
        return this.isCurrentUser ? '我的' : '他的'
      }
    },
    methods: {
      getFollowers () {
        this.isLoadingFollowers = true
        getFollowersByPage(this.userId, this.currentPage - 1, this.pageSize).then((response) => {
          this.isLoadingFollowers = false
          this.total = response.result.totalElements
          this.followers = response.result.content
        }).catch((e) => {
          Message({
            message: '获取粉丝信息失败，请稍后再试！',
            type: 'error',
            duration: 1000
          })
        })
      },
      handleCurrentChange (value) {
        this.currentPage = value
        this.getFollowers()
      }
    }
  }
</script>
