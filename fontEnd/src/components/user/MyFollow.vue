<template>
  <el-tabs v-model="activeName">
    <el-tab-pane v-loading.lock="follow_user.isLoadingData" label="关注的人" name="follow_user">
      <div v-for="follower in follow_user.followed">
        <follower :follower="follower"></follower>
      </div>
      <div v-show="showPagination(follow_user)" class="pagination">
        <el-pagination
          @current-change="handleCurrentChange_user"
          :current-page.sync="follow_user.currentPage"
          :page-size="follow_user.pageSize"
          layout="prev, pager, next, jumper"
          :total="follow_user.total">
        </el-pagination>
      </div>
    </el-tab-pane>
    <el-tab-pane v-loading.lock="follow_course.isLoadingData" label="关注的课程" name="follow_course">
      <div v-for="course in follow_course.courses">
        <follow-course :course="course"></follow-course>
      </div>
      <div v-show="showPagination(follow_course)" class="pagination">
        <el-pagination
          @current-change="handleCurrentChange_course"
          :current-page.sync="follow_course.currentPage"
          :page-size="follow_course.pageSize"
          layout="prev, pager, next, jumper"
          :total="follow_course.total">
        </el-pagination>
      </div>
    </el-tab-pane>
    <el-tab-pane v-loading.lock="follow_question.isLoadingData" label="关注的问题" name="follow_question">
      <div v-for="question in follow_question.questions">
        <follow-question :question="question"></follow-question>
      </div>
      <div v-show="showPagination(follow_question)" class="pagination">
        <el-pagination
          @current-change="handleCurrentChange_question"
          :current-page.sync="follow_question.currentPage"
          :page-size="follow_question.pageSize"
          layout="prev, pager, next, jumper"
          :total="follow_question.total">
        </el-pagination>
      </div>
    </el-tab-pane>
  </el-tabs>
</template>
<script>
  import { getFollowedByPage, getUserFollowCourse, getUserFollowQuestion } from '@/api/user'
  import { Message } from 'element-ui'
  import Follower from './components/FollowerComponent.vue'
  import FollowCourse from './components/FollowCourseComponent.vue'
  import FollowQuestion from './components/FollowQuestionComponent.vue'
  export default {
    components: { 'follower': Follower, 'follow-course': FollowCourse, 'follow-question': FollowQuestion },
    data () {
      return {
        activeName: 'follow_user',
        follow_user: {
          currentPage: 1,
          pageSize: 10,
          followed: [],
          total: 0,
          isLoadingData: false
        },
        follow_course: {
          currentPage: 1,
          pageSize: 10,
          courses: [],
          total: 0,
          isLoadingData: false
        },
        follow_question: {
          currentPage: 1,
          pageSize: 10,
          questions: [],
          total: 0,
          isLoadingData: false
        },
        userId: null
      }
    },
    mounted: function () {
      this.userId = this.$route.params.userId
      this.updateDataAccordingTab()
    },
    methods: {
      updateDataAccordingTab () {
        if (this.activeName === 'follow_user') {
          this.getUserFollowed()
        } else if (this.activeName === 'follow_course') {
          this.getUserFollowCourse()
        } else if (this.activeName === 'follow_question') {
          this.getUserFollowQuestion()
        }
      },
      getUserFollowed () {
        this.follow_user.isLoadingData = true
        getFollowedByPage(this.userId, this.follow_user.currentPage - 1, this.follow_user.pageSize).then((response) => {
          this.follow_user.total = response.result.totalElements
          this.follow_user.followed = response.result.content
          this.follow_user.isLoadingData = false
        }).catch((e) => {
          Message({
            message: '获取用户关注信息失败，请稍后再试！',
            type: 'error',
            duration: 1000
          })
        })
      },
      getUserFollowCourse () {
        this.follow_course.isLoadingData = true
        getUserFollowCourse(this.userId, this.follow_course.currentPage - 1, this.follow_course.pageSize).then((response) => {
          this.follow_course.total = response.result.totalElements
          this.follow_course.courses = response.result.content
          this.follow_course.isLoadingData = false
        }).catch((e) => {
          Message({
            message: '获取用户关注的课程信息失败，请稍后再试！',
            type: 'error',
            duration: 1000
          })
        })
      },
      getUserFollowQuestion () {
        this.follow_question.isLoadingData = true
        getUserFollowQuestion(this.userId, this.follow_question.currentPage - 1, this.follow_question.pageSize).then((response) => {
          this.follow_question.isLoadingData = false
          this.follow_question.total = response.result.totalElements
          this.follow_question.questions = response.result.content
        }).catch((e) => {
          Message({
            message: '获取用户关注的问题失败，请稍后再试！',
            type: 'error',
            duration: 1000
          })
        })
      },
      showPagination (tab) {
        return tab.total > tab.pageSize
      },
      handleCurrentChange_user (value) {
        this.getUserFollowed()
      },
      handleCurrentChange_course (value) {
        this.getUserFollowCourse()
      },
      handleCurrentChange_question (value) {
        this.getUserFollowQuestion()
      }
    },
    watch: {
      activeName: function () {
        this.updateDataAccordingTab()
      }
    }
  }
</script>
