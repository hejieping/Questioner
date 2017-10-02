import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Index from '@/components/Index'
import All from '@/components/All'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'index',
      component: Index,
      meta: {needAuth: false}
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
