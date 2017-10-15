import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Index from '@/components/Index'
import PersonInfo from '@/components/PersonInfo'
import Content from '@/components/main-content/Content'
import QuestionList from '@/components/main-content/QuestionList'
import EditQuestion from '@/components/question/EditQuestion'
import ShowContent from '@/components/ShowContent'
import PersonQuestion from '@/components/PersonQuestion'
import QuestionDetail from '@/components/question/QuestionDetail'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'index',
      component: Index,
      children: [
        {
          name: 'index',
          path: '/',
          component: Content,
          children: [
            {
              name: 'allQuestion',
              path: '/',
              component: QuestionList,
              meta: {needAuth: false}
            }
          ]
        },
        {
          name: 'personInfo', path: '/personInfo', component: PersonInfo, meta: {needAuth: false}
        },
        {
          name: 'editQuestion', path: '/editQuestion', component: EditQuestion, meta: {needAuth: false}
        },
        {
          name: 'questionContent',
          path: 'questions',
          component: Content,
          children: [
            {
              name: 'questions', path: '/questions/:questionType', component: QuestionList, meta: {needAuth: false}
            }
          ]
        },
        {
          name: 'ShowContent', path: '/showContent', component: ShowContent, meta: {needAuth: false}
        },
        {
          name: 'personalQuestion', path: '/personalQuestion/:questionId', component: PersonQuestion, meta: {needAuth: false}
        },
        {
          name: 'questionDetail', path: '/questionDetail/:questionId', component: QuestionDetail, meta: {needAuth: false}
        }
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: Login,
      meta: {needAuth: false}
    }
  ]
})
