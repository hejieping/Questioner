/**
 * Created by qi on 2017/10/26.
 */
import fetch from '@/utils/fetch'

export function getUnreadNotice () {
  return fetch({
    url: '/notice/getUnreadNotice',
    method: 'get'
  })
}

export function markNoticeHasRead (noticeId) {
  return fetch({
    url: '/notice/readNotice/' + noticeId,
    method: 'put'
  })
}
