/**
 * Created by qi on 2017/10/18.
 */
import fetch from '@/utils/fetch'

export function getFollowStatus (questionTypeId, userId) {
  return fetch({
    url: '/questionType/hasFollow',
    method: 'get',
    params: {
      questionTypeId: questionTypeId,
      userId: userId
    }
  })
}

export function followQuestionType (questionTypeId, userId) {
  return fetch({
    url: '/questionType/follow',
    method: 'put',
    params: {
      questionTypeId: questionTypeId,
      userId: userId
    }
  })
}

export function unFollowQuestionType (questionTypeId, userId) {
  return fetch({
    url: '/questionType/unFollow',
    method: 'put',
    params: {
      questionTypeId: questionTypeId,
      userId: userId
    }
  })
}
