/**
 * Created by qi on 2017/10/16.
 */
import fetch from '@/utils/fetch'

export function postAnswerComment (answerId, commentContent) {
  const data = {
    commentContent: commentContent
  }
  return fetch({
    url: '/answerComment/' + answerId,
    method: 'post',
    data
  })
}

export function getAnswerCommentsOfAnswer (answerId) {
  return fetch({
    url: '/answerComment/' + answerId,
    method: 'get'
  })
}

