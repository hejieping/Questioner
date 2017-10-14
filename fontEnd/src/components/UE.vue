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
  import '../../static/UE/ueditor.all.js'
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
      },
      fullscreen: {
        type: Boolean,
        default: true
      }
    },
    mounted () {
      this.config.toolbars = [[
        'source', '|', 'undo', 'redo', '|',
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
      if (this.fullscreen) {
        this.config.toolbars[0].push('fullscreen')
      }
      this.editor = window.UE.getEditor('editor', this.config)
    },
    methods: {
      getUEditor () {
        return this.editor
      }
    },
    destroyed () {
      this.editor.destroy()
    }
  }
</script>
