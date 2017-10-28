<template>
  <div>
    <h3 class="reputation_heading">
      <span>声望记录</span>
    </h3>
    <div class="reputation-record-container" v-loading.lock="isLoadingData" element-loading-text="玩命加载中">
      <div class="reputation-record" v-for="record in reputationRecords">
        <el-row>
          <el-col :span="20">
            <el-col :span="2">
              <el-tag v-if="isGood(record)" style="font-size: 20px" type="success"> +{{ getScore(record) }}</el-tag>
              <el-tag v-else style="font-size: 20px" type="danger"> {{ getScore(record) }} </el-tag>
            </el-col>
            <el-col :span="4">
              <span style="color: #666" class="description">{{ creditLabel(record) }} </span>
            </el-col>
            <el-col :span="12">
              <router-link :to="{ path: `/questionDetail/${record.questionId}/${record.answerId}` }">
                <span class="questionTitle" style="margin-left: 10px">
                  {{ record.questionTitle }}
                </span>
              </router-link>
            </el-col>
          </el-col>
          <el-col :span="4" style="text-align: right; padding-right: 10px">
            {{ $moment(record.feedbackDateTime).fromNow() }}
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
<style>
  .reputation_heading {
    padding-bottom: 20px;
    border-bottom: solid 1px #666666;
  }
  .reputation-record {
    padding: 10px 0;
    border-bottom: 1px solid #666666;
    margin-top: 0;
  }
  .reputation-record a {
    color: #20a0ff;
  }
  .reputation-record a:hover {
    text-decoration: underline;
  }
</style>
<script>
  import { creditScoreMap, creditLabelMap } from '../../utils/util'
  import { getUserReputationRecord } from '@/api/reputationRecord'
  import { Message } from 'element-ui'
  export default {
    data: function () {
      return {
        reputationRecords: [],
        isLoadingData: false,
        currentPage: 1,
        total: 0,
        pageSize: 10,
        userId: null
      }
    },
    mounted: function () {
      this.userId = this.$route.params.userId
      this.getReputationRecords()
    },
    computed: {
      showPagination () {
        return this.total > this.pageSize
      }
    },
    methods: {
      getReputationRecords () {
        this.isLoadingData = true
        getUserReputationRecord(this.userId, this.currentPage - 1, this.pageSize).then(response => {
          if (response.status === '200') {
            this.total = response.result.totalNumber
            this.reputationRecords = response.result.reputationRecords
          }
          this.isLoadingData = false
        }).catch((e) => {
          Message({
            message: '获取用户信誉积分失败，请稍后重试！',
            type: 'error',
            duration: 1000
          })
        })
      },
      handleCurrentChange () {
        this.getReputationRecords()
      },
      isGood (record) {
        return creditScoreMap[record.reputationType] > 0
      },
      getScore (record) {
        return creditScoreMap[record.reputationType]
      },
      creditLabel (record) {
        return creditLabelMap[record.reputationType]
      }
    }
  }
</script>
