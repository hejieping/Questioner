/**
 * Created by qi on 2017/10/18.
 */
import fetch from '@/utils/fetch'

export function getFollowStatus (questionTypeId) {
  return fetch({
    url: '/questionType/hasFollow',
    method: 'get',
    params: {
      questionTypeId: questionTypeId
    }
  })
}

export function followQuestionType (questionTypeId) {
  return fetch({
    url: '/questionType/follow',
    method: 'put',
    params: {
      questionTypeId: questionTypeId
    }
  })
}

export function unFollowQuestionType (questionTypeId) {
  return fetch({
    url: '/questionType/unFollow',
    method: 'put',
    params: {
      questionTypeId: questionTypeId
    }
  })
}
