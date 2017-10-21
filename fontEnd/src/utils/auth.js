/**
 * Created by qi on 2017/9/3.
 */
const TokenKey = 'userToken'
const UserKey = 'MyUserKey'
export function getToken () {
  return sessionStorage.getItem(TokenKey)
}

export function setToken (token) {
  return sessionStorage.setItem(TokenKey, token)
}

export function removeToken () {
  return sessionStorage.removeItem(TokenKey)
}

export function setUser (user) {
  return sessionStorage.setItem(UserKey, JSON.stringify(user))
}

export function getUser () {
  const userStr = sessionStorage.getItem(UserKey)
  return JSON.parse(userStr)
}

export function removeUser () {
  return sessionStorage.removeItem(UserKey)
}
