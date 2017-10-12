<template>
  <div id="content1234" v-html="content">
  </div>
</template>
<style>

</style>
<script>
  /* eslint-disable no-duplicate-imports */

  import '../../static/UE/ueditor.parse'
  export default {
    name: 'showContent',
    data () {
      return {
        content: ''
      }
    },
    mounted () {
      this.content = '<pre class="brush:cpp;toolbar:false">#include&lt;iostream&gt;\nusing&nbsp;namespace&nbsp;std&nbsp;;\nint&nbsp;main(){\n&nbsp;&nbsp;&nbsp;&nbsp;cout&nbsp;&lt;&lt;&nbsp;&quot;Hello&nbsp;World&quot;;\n}</pre>'

      this.$nextTick(function () {
        window.uParse('#content1234', {
          rootPath: '../../static/UE/'
        })
      })
    }
  }
</script>
