import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Hello from '@/components/Hello'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'hello',
      component: Hello,
      hidden: true
    },
    {
      path: '/login',
      name: 'login',
      component: Login,
      hidden: true
    }
  ]
})
