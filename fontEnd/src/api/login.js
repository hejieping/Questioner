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

export function validateLoginUsername (loginUsername) {
  return fetch({
    url: '/validateLoginUsername/' + loginUsername,
    method: 'get'
  })
}

export function register (loginUsername, username, password) {
  return fetch({
    url: '/register',
    method: 'post',
    data: {
      loginUsername: loginUsername,
      username: username,
      password: password
    }
  })
}

export function getUserOwnInfo () {
  return fetch({
    url: '/userOwnInfo',
    method: 'get'
  })
}
