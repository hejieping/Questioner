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
