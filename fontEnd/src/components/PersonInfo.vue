<template>
  <div id="personInfo">
    个人资料
    <router-link to="/personalQuestion/1">
      问题一
    </router-link>
    <div id="uploadImagePanel">
      <a class="btn" @click="toggleShow">设置头像</a>
      <my-upload field="avatar"
                 @crop-success="cropSuccess"
                 @crop-upload-success="cropUploadSuccess"
                 @crop-upload-fail="cropUploadFail"
                 v-model="show"
                 :width="100"
                 :height="100"
                 url="http://localhost:8080/uploadAvatar"
                 :params="params"
                 :headers="headers"
                 img-format="png"></my-upload>
      <img style="border-radius: 50px" :src="imgDataUrl">
    </div>
  </div>
</template>
<script>
  import myUpload from 'vue-image-crop-upload'

  export default {
    data () {
      return {
        show: false,
        params: {
          token: '123456789',
          name: 'avatar'
        },
        headers: {
          smail: '*_~'
        },
        imgDataUrl: 'http://localhost:8080/img/ueditor/20171011/1507732693731046207.jpg'
      }
    },
    components: {
      'my-upload': myUpload
    },
    methods: {
      toggleShow () {
        this.show = !this.show
      },
      cropSuccess (imgDataUrl, field) {
        this.imgDataUrl = imgDataUrl
        console.log('------- crop success -------')
      },
      cropUploadSuccess (jsonData, field) {
        console.log('----- upload success -----')
        console.log(jsonData)
        console.log('field: ' + field)
      },
      cropUploadFail (status, field) {
        console.log(' ----- upload fail ----- ')
        console.log(status)
        console.log('field: ' + field)
      }
    }
  }
</script>
