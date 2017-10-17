/**
 * Created by qi on 2017/10/16.
 */
import fetch from '@/utils/fetch'

export function postAnswerComment (answerId, accountId, commentContent) {
  const data = {
    commentContent: commentContent,
    commenter: {
      id: accountId
    }
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

