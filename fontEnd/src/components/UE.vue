<template>
  <div>
    <script name="content" id="editor" type="text/plain"></script>
  </div>
</template>
<style>
  #editor div.edui-editor{
    z-index: 0 !important;
  }
</style>
<script>
  import '../../static/UE/ueditor.config'
  import '../../static/UE/ueditor.all.min.js'
  import '../../static/UE/lang/zh-cn/zh-cn'
  import '../../static/UE/ueditor.parse.min'
  import '../../static/UE/kityformula-plugin/addKityFormulaDialog'
  import '../../static/UE/kityformula-plugin/getKfContent'
  import '../../static/UE/kityformula-plugin/defaultFilterFix'
  export default {
    name: 'UE',
    data () {
      return {
        editor: null
      }
    },
    props: {
      defaultMsg: {
        type: String
      },
      config: {
        type: Object
      },
      name: {
        type: String
      }
    },
    mounted () {
      const _this = this
      this.config.toolbars = [[
        'fullscreen', 'source', '|', 'undo', 'redo', '|',
        'bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc', '|',
        'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
        'customstyle', 'paragraph', 'fontfamily', 'fontsize', '|',
        'directionalityltr', 'directionalityrtl', 'indent', '|',
        'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
        'link', 'unlink', 'anchor', '|', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
        'simpleupload', 'insertimage', 'emotion', 'scrawl', 'insertvideo', 'music', 'attachment', 'map', 'insertframe', 'insertcode', 'pagebreak', 'template', 'background', '|',
        'horizontal', 'date', 'time', 'spechars', 'wordimage', '|',
        'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow', 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts', '|',
        'print', 'preview', 'searchreplace', 'drafts', 'kityformula'
      ]]
      this.editor = window.UE.getEditor('editor', this.config)
      this.editor.addListener('ready', function () {
        _this.editor.setContent(_this.defaultMsg)
      })
    },
    methods: {
      getUEContent () {
        return this.editor.getContent()
      }
    },
    destroyed () {
      this.editor.destroy()
    }
  }
</script>
