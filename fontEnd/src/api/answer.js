/**
 * Created by qi on 2017/10/17.
 */
import fetch from '@/utils/fetch'

export function giveAnswerFeedback (answerId, isGood) {
  return fetch({
    url: '/answer/giveFeedback',
    method: 'put',
    params: {
      answerId: answerId,
      isGood: isGood
    }
  })
}

export function getQuestionSingleAnswer (questionId, answerId) {
  return fetch({
    url: '/answer/' + questionId + '/' + answerId,
    method: 'get'
  })
}

export function acceptAnswer (answerId) {
  return fetch({
    url: '/answer/acceptAnswer',
    method: 'put',
    params: {
      answerId: answerId
    }
  })
}

export function getUserAnswerCount (userId) {
  return fetch({
    url: '/answer/getUserAnswerCount/' + userId,
    method: 'get'
  })
}
