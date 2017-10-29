// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import Element from 'element-ui'
import store from './store'
import 'element-ui/lib/theme-default/index.css'
import VueParticles from 'vue-particles'
import 'font-awesome/css/font-awesome.css'

const moment = require('moment')
require('moment/locale/zh-cn')
Vue.use(Element)
Vue.use(require('vue-moment'), {
  moment
})
Vue.use(VueParticles)
Vue.config.productionTip = false

router.beforeEach((to, from, next) => {
  if (to.meta.needAuth === false) {
    next()
    return
  }
  let token = store.getters.token
  if (token !== null && to.path === '/login') {
    next('/')
  } else if (token === null && to.path !== '/login') {
    next({ path: '/login' })
  } else {
    next()
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App }
})
