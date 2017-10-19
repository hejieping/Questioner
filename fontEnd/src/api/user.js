/**
 * Created by qi on 2017/10/18.
 */
import fetch from '@/utils/fetch'

export function getUser (userId) {
  return fetch({
    url: '/user/' + userId,
    type: 'get'
  })
}

export function getFollowInfo (userId) {
  return fetch({
    url: '/user/followersInfo/' + userId,
    type: 'get'
  })
}

export function getFollowersByPage (userId, currentPage, pageSize) {
  return fetch({
    url: '/user/getFollowers/' + userId,
    params: {
      currentPage: currentPage,
      pageSize: pageSize
    }
  })
}

export function getFollowedByPage (userId, currentPage, pageSize) {
  return fetch({
    url: '/user/getFollowed/' + userId,
    params: {
      currentPage: currentPage,
      pageSize: pageSize
    }
  })
}

export function followUser (userId, followedId) {
  return fetch({
    url: '/user/follow',
    type: 'put',
    params: {
      userId: userId,
      followedId: followedId
    }
  })
}

export function unFollowUser (userId, followedId) {
  return fetch({
    url: '/user/unFollow',
    type: 'put',
    params: {
      userId: userId,
      followedId: followedId
    }
  })
}

export function getUserFollowCourse (userId, currentPage, pageSize) {
  return fetch({
    url: '/questionType/getFollow/' + userId,
    type: 'get',
    params: {
      currentPage: currentPage,
      pageSize: pageSize
    }
  })
}

export function getUserFollowQuestion (userId, currentPage, pageSize) {
  return fetch({
    url: '/question/getFollow/' + userId,
    type: 'get',
    params: {
      currentPage: currentPage,
      pageSize: pageSize
    }
  })
}

export function getUserQuestionByViews (userId, currentPage, pageSize) {
  return fetch({
    url: '/question/getUserQuestionByViews/' + userId,
    type: 'get',
    params: {
      currentPage: currentPage,
      pageSize: pageSize
    }
  })
}

export function getUserQuestionByDateTime (userId, currentPage, pageSize) {
  return fetch({
    url: '/question/getUserQuestionByDateTime/' + userId,
    type: 'get',
    params: {
      currentPage: currentPage,
      pageSize: pageSize
    }
  })
}
