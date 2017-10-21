<template>
  <div>
    <h3 class="question_heading">
      <span>{{ infoPrefix }}提问</span>
      <span class="sort-box" style="float: right">
        <el-radio-group v-model="sortParam">
          <el-radio-button label="时间"></el-radio-button>
          <el-radio-button label="热度"></el-radio-button>
        </el-radio-group>
      </span>
    </h3>
    <div class="question-container" v-loading.lock="isLoadingData">
      <div v-for="question in questions" class="question">
        <el-row>
          <el-col :span="20">
            <el-tag>
              {{ question.views }} 浏览
            </el-tag>
            <span @click="$router.push({ path: `/questionDetail/${question.id}/` })" style="margin-left: 10px" class="question-title">
              <a href="javascript:void 0">{{ question.questionTitle }}</a>
            </span>
          </el-col>
          <el-col :span="4">
            {{ question.publishDateTime | moment('YYYY年MM月DD日') }}
          </el-col>
        </el-row>
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
 </div>
</template>
<style scoped>
  .question_heading {
    padding-bottom: 20px;
    border-bottom: solid 1px grey;
  }
  .question {
    padding: 10px 0;
    border-bottom: 1px solid grey;
    margin-top: 0;
  }
  .question a {
    color: #20a0ff;
  }
  .question a:hover {
    text-decoration: underline;
  }
</style>
<script>
  import { getUserQuestionByDateTime, getUserQuestionByViews } from '@/api/user'
  import { mapGetters } from 'vuex'
  export default {
    data () {
      return {
        sortParam: '时间',
        sortByDateTime: true,
        isLoadingData: false,
        total: 0,
        currentPage: 1,
        pageSize: 10,
        questions: [],
        userId: ''
      }
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
      getQuestion () {
        if (this.sortByDateTime) {
          this.getQuestionByDateTime()
        } else {
          this.getQuestionByViews()
        }
      },
      getQuestionByDateTime () {
        this.isLoadingData = true
        getUserQuestionByDateTime(this.userId, this.currentPage - 1, this.pageSize).then((response) => {
          this.questions = response.result.content
          this.total = response.result.totalElements
          this.isLoadingData = false
        })
      },
      getQuestionByViews () {
        this.isLoadingData = true
        getUserQuestionByViews(this.userId, this.currentPage - 1, this.pageSize).then((response) => {
          this.questions = response.result.content
          this.total = response.result.totalElements
          this.isLoadingData = false
        })
      },
      handleCurrentChange () {
        this.getQuestion()
      }
    },
    mounted: function () {
      this.userId = this.$route.params.userId
      this.getQuestion()
    },
    watch: {
      sortParam: function () {
        this.sortByDateTime = !this.sortByDateTime
        this.getQuestion()
      }
    }
  }
</script>
