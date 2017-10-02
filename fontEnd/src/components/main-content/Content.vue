<template>
  <ul>
    <list v-for="(content, index) in lists"
          :content="content" v-on:remove="removeList(index)">
    </list>
  </ul>
</template>
<script>
  import List from './ContentItem.vue'
  import bus from '../../assets/eventBus'
  import fetch from '@/utils/fetch'
  import $ from 'jquery'

  export default{
    data () {
      return {
        lists: []
      }
    },
    components: {
      'list': List
    },
    methods: {
      removeList: function (index) {
        this.lists.splice(index, 1)
      },
      getContent: function () {
        fetch(
          {
            url: '/data/content.json',
            method: 'GET'
          }
          )
          .then((res) => {
            this.lists = res
          })
      }
    },
    mounted: function () {
      this.$nextTick(() => {
        this.getContent()
      })
      bus.$on('search', (msg) => {
        console.log(msg)
      })
    }
  }
</script>
