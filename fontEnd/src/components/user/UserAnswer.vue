<template>
  <div>
    <h3 class="answers_heading">
      <span>{{ infoPrefix }}回答</span>
      <span class="sort-box" style="float: right">
        <el-radio-group v-model="sortParam">
          <el-radio-button label="时间"></el-radio-button>
          <el-radio-button label="得票"></el-radio-button>
        </el-radio-group>
      </span>
    </h3>
    <div class="answers-container" v-loading.lock="isLoadingData" element-loading-text="玩命加载中">
      <div class="answer" v-for="answer in answers">
        <el-row>
          <el-col :span="20">
            <el-tag type="success"><i class="fa fa-thumbs-o-up"></i> {{ answer.thumbsUpCount }}</el-tag>
            <el-tag> <i class="fa fa-thumbs-o-down"></i> {{ answer.thumbsDownCount }}</el-tag>
            <router-link :to="{ path: `/questionDetail/${answer.questionId}/${answer.id}` }">
              <span class="questionTitle" style="margin-left: 10px">
                {{ answer.questionTitle }}
            </span>
            </router-link>
          </el-col>
          <el-col :span="4" style="text-align: right; padding-right: 10px">
            {{ answer.answerDateTime | moment('YYYY年MM月DD日') }}
          </el-col>
        </el-row>
      </div>
    </div>
    <div v-show="showPagination" class="pagination">
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-size="pageSize"
        layout="prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
  </div>
</template>
<style scoped>
  .answers_heading {
    padding-bottom: 20px;
    border-bottom: solid 1px grey;
  }
  .answer {
    padding: 10px 0;
    border-bottom: 1px solid grey;
    margin-top: 0;
  }
  .answer a {
    color: #20a0ff;
  }
  .answer a:hover {
    text-decoration: underline;
   }
</style>
<script>
  import { getUserAnswerByDateTime, getUserAnswersByThumbsUpCount } from '@/api/user'
  import { Message } from 'element-ui'
  import { mapGetters } from 'vuex'
  export default {
    data () {
      return {
        sortParam: '时间',
        isSortByDateTime: true,
        isLoadingData: false,
        answers: [],
        userId: '',
        currentPage: 1,
        total: 0,
        pageSize: 10
      }
    },
    mounted: function () {
      this.userId = this.$route.params.userId
      this.getUserAnswer()
    },
    computed: {
      showPagination () {
        return this.total > this.pageSize
      },
      ...mapGetters(['user']),
      isCurrentUser: function () {
        return this.user !== null && parseInt(this.user.id) === parseInt(this.userId)
      },
      infoPrefix: function () {
        return this.isCurrentUser ? '我的' : '他的'
      }
    },
    methods: {
      getUserAnswer () {
        if (this.isSortByDateTime) {
          this.getUserAnswerByDatTime()
        } else {
          this.getUserAnswerByThumbsUpCount()
        }
      },
      getUserAnswerByDatTime () {
        this.isLoadingData = true
        getUserAnswerByDateTime(this.userId, this.currentPage - 1, this.pageSize).then((response) => {
          this.answers = response.result.content
          this.total = response.result.total
          this.isLoadingData = false
        }).catch((e) => {
          this.showError()
        })
      },
      getUserAnswerByThumbsUpCount () {
        this.isLoadingData = true
        getUserAnswersByThumbsUpCount(this.userId, this.currentPage - 1, this.pageSize).then((response) => {
          this.answers = response.result.content
          this.total = response.result.total
          this.isLoadingData = false
        }).catch((e) => {
          this.showError()
        })
      },
      showError () {
        Message({
          message: '得到用户发表的回答失败，请稍后重试!',
          type: 'error',
          duration: 1000
        })
      },
      handleCurrentChange () {
        this.getUserAnswer()
      }
    },
    watch: {
      sortParam: function () {
        this.isSortByDateTime = !this.isSortByDateTime
        this.getUserAnswer()
      }
    }
  }
</script>
