/**
 * Created by qi on 2017/10/28.
 */
import fetch from '@/utils/fetch'

export function getUserReputationRecord (userId, currentPage, pageSize) {
  return fetch({
    url: '/creditRecord/' + userId,
    methods: 'get',
    params: {
      currentPage: currentPage,
      pageSize: pageSize
    }
  })
}
