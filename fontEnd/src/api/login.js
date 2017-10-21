/**
 * Created by qi on 2017/9/3.
 */
import fetch from '@/utils/fetch'

export function login (username, password) {
  const data = {
    username: username,
    password: password
  }
  return fetch({
    url: '/auth',
    method: 'post',
    data
  })
}

export function getUserOwnInfo () {
  return fetch({
    url: '/userOwnInfo',
    method: 'get'
  })
}
