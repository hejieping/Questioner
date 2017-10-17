/**
 * Created by qi on 2017/10/11.
 */
import fetch from '@/utils/fetch'

export function raiseQuestion (userId, questionTitle, questionTypeId, questionContent, questionContentTxt) {
  const data = {
    questionTitle: questionTitle,
    questionContent: questionContent,
    questionContentTxt: questionContentTxt,
    questionType: {
      id: questionTypeId
    },
    publisher: {
      id: userId
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

export function postAnswer (answerContent, questionId, userId) {
  const data = {
    answerContent: answerContent,
    account: {
      id: userId
    }
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

export function hasFollowQuestion (questionId, userId) {
  return fetch({
    url: '/question/hasFollow',
    method: 'get',
    params: {
      questionId: questionId,
      userId: userId
    }
  })
}

export function unFollowQuestion (questionId, userId) {
  return fetch({
    url: '/question/unFollowQuestion',
    method: 'put',
    params: {
      questionId: questionId,
      userId: userId
    }
  })
}

export function followQuestion (questionId, userId) {
  return fetch({
    url: '/question/followQuestion',
    method: 'put',
    params: {
      questionId: questionId,
      userId: userId
    }
  })
}

