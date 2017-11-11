<template>
  <div id="home-container">
    <div class="panel panel-default user-profile">
      <div class="panel-heading">
        <h3>个人喜好</h3>
      </div>
      <div class="panel-body">
        <div class="diagram">
          <div element-loading-text="玩命加载中" v-loading="is_loading" id="user-preference" class="content" style="height: 350px;">

          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<style>
  .panel-default {
    border-color: #ddd;
  }
  .panel {
    margin-bottom: 20px;
    background-color: #fff;
    border: 1px solid transparent;
    border-radius: 4px;
    -webkit-box-shadow: 0 1px 1px rgba(0,0,0,0.05);
    box-shadow: 0 1px 1px rgba(0,0,0,0.05);
  }
  .panel-default>.panel-heading {
    color: #333;
    background-color: #f5f5f5;
    border-color: #ddd;
  }
  .panel-heading {
    padding: 10px 15px;
    border-bottom: 1px solid transparent;
    border-top-right-radius: 3px;
    border-top-left-radius: 3px;
  }
  .panel-body {
    padding: 15px;
  }
  .user-profile .diagram {
    height: 400px;
  }
</style>
<script>
  import echarts from 'echarts'
  import { getUserPreference } from '@/api/user'
  import { Message } from 'element-ui'
  export default {
    data () {
      return {
        userId: '',
        is_loading: false
      }
    },
    mounted () {
      this.userId = this.$route.params.userId
      this.getUserPreference(this.userId)
    },
    methods: {
      getUserPreference (userId) {
        let course = []
        let data = []
        this.is_loading = true
        getUserPreference(userId, 8).then((response) => {
          if (response.status === '200') {
            const userPreference = response.result
            for (let i = 0; i < userPreference.length; i++) {
              const preference = userPreference[i]
              const preferenceName = preference['subject'] + ' -> ' + preference['course']
              course.push(preferenceName)
              data.push({
                value: preference['preferenceValue'],
                name: preferenceName
              })
            }
          }
          let option = {
            title: {
              text: '用户喜好',
              x: 'center'
            },
            tooltip: {
              trigger: 'item',
              formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            legend: {
              orient: 'vertical',
              left: 'left',
              data: course
            },
            series: [
              {
                name: '课程',
                type: 'pie',
                radius: '70%',
                center: ['50%', '60%'],
                data: data,
                itemStyle: {
                  emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                  }
                }
              }
            ]
          }
          const myChart = echarts.init(document.getElementById('user-preference'))
          myChart.setOption(option)
          this.is_loading = false
        }).catch((e) => {
          Message({
            message: '对不起，不能加载用户喜好数据！',
            type: 'error',
            duration: 2000
          })
        })
      }
    }
  }
</script>
