import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Hello from '@/components/Hello'
import All from '@/components/All'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'hello',
      component: Hello,
      meta: {needAuth: true}
    },
    {
      path: '/login',
      name: 'login',
      component: Login,
      meta: {needAuth: false}
    },
    {
      path: '/all',
      name: 'all',
      component: All,
      meta: {needAuth: false}
    }
  ]
})
