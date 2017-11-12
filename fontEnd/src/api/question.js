/**
 * Created by qi on 2017/10/11.
 */
import fetch from '@/utils/fetch'

export function raiseQuestion (questionTitle, questionTypeId, questionContent, questionContentTxt) {
  const data = {
    questionTitle: questionTitle,
    questionContent: questionContent,
    questionContentTxt: questionContentTxt,
    questionType: {
      id: questionTypeId
    }
  }
  return fetch({
    url: '/question',
    method: 'post',
    data
  })
}

export function getAllQuestion (questionTitle, currentPage, pageSize, sortParam) {
  return fetch({
    url: '/question',
    method: 'get',
    params: {
      questionTitle: questionTitle,
      currentPage: currentPage,
      pageSize: pageSize,
      sortParam: sortParam
    }
  })
}

export function getRecommendQuestion (recommendSize = 3) {
  return fetch({
    url: '/question/recommend/',
    method: 'get',
    params: {
      questionSize: recommendSize
    }
  })
}

export function getQuestionByType (questionTitle, questionTypeId, currentPage, pageSize, sortParam) {
  return fetch({
    url: '/question/getQuestionByType/' + questionTypeId,
    method: 'get',
    params: {
      questionTitle: questionTitle,
      currentPage: currentPage,
      pageSize: pageSize,
      sortParam: sortParam
    }
  })
}

export function getQuestion (questionId) {
  return fetch({
    url: '/question/' + questionId,
    method: 'get'
  })
}

export function getQuestionType () {
  return fetch({
    url: '/questionType',
    method: 'get'
  })
}

export function postAnswer (answerContent, questionId) {
  const data = {
    answerContent: answerContent
  }
  return fetch({
    url: '/answer/' + questionId,
    method: 'post',
    data
  })
}

export function getAnswerNum (questionId) {
  return fetch({
    url: '/answer/getAnswerNum/' + questionId,
    method: 'get'
  })
}

export function getLimitAnswer (questionId, startIndex, limitNum, sortParam) {
  return fetch({
    url: '/answer/getLimitAnswers',
    method: 'get',
    params: {
      questionId: questionId,
      startIndex: startIndex,
      limitNum: limitNum,
      sortParam: sortParam
    }
  })
}

export function hasFollowQuestion (questionId) {
  return fetch({
    url: '/question/personal/hasFollow',
    method: 'get',
    params: {
      questionId: questionId
    }
  })
}

export function unFollowQuestion (questionId) {
  return fetch({
    url: '/question/personal/unFollowQuestion',
    method: 'put',
    params: {
      questionId: questionId
    }
  })
}

export function followQuestion (questionId) {
  return fetch({
    url: '/question/personal/followQuestion',
    method: 'put',
    params: {
      questionId: questionId
    }
  })
}

export function getUserQuestionCount (userId) {
  return fetch({
    url: '/question/getUserQuestionCount/' + userId,
    methods: 'get'
  })
}
