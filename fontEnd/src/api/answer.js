/**
 * Created by qi on 2017/10/17.
 */
import fetch from '@/utils/fetch'

export function giveAnswerFeedback (answerId, userId, isGood) {
  return fetch({
    url: '/answer/giveFeedback',
    method: 'put',
    params: {
      answerId: answerId,
      userId: userId,
      isGood: isGood
    }
  })
}
