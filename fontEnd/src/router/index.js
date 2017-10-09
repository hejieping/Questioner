import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Index from '@/components/Index'
import All from '@/components/All'
import PersonInfo from '@/components/PersonInfo'
import Content from '@/components/Content'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'index',
      component: Index,
      children: [
        {
          name: 'index', path: '', component: Content, meta: {needAuth: false}
        },
        {
          name: 'personInfo', path: 'personInfo', meta: {needAuth: false}
        }
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: Login,
      meta: {needAuth: false}
    },
    {
      path: '/personInfo',
      name: 'personInfo',
      component: PersonInfo,
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
