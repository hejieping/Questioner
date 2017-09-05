/**
 * Created by qi on 2017/9/4.
 */
import fetch from '@/utils/fetch'

export function testAdmin () {
  return fetch(
    {
      url: '/admin',
      method: 'GET'
    }
  )
}

export function testUser () {
  return fetch({
    url: '/user',
    method: 'post',
    data: {}
  })
}
