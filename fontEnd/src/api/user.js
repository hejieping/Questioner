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

export function hasFollowUser (followedId) {
  return fetch({
    url: '/user/hasFollow/' + followedId,
    type: 'get'
  })
}

export function followUser (followedId) {
  return fetch({
    url: '/user/follow/' + followedId,
    type: 'get'
  })
}

export function unFollowUser (followedId) {
  return fetch({
    url: '/user/unFollow/' + followedId,
    type: 'get'
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

export function getUserAnswerByDateTime (userId, currentPage, pageSize) {
  return fetch({
    url: '/answer/getUserAnswersByDateTime/' + userId,
    type: 'get',
    params: {
      currentPage: currentPage,
      pageSize: pageSize
    }
  })
}

export function getUserAnswersByThumbsUpCount (userId, currentPage, pageSize) {
  return fetch({
    url: '/answer/getUserAnswersByThumbsUpCount/' + userId,
    type: 'get',
    params: {
      currentPage: currentPage,
      pageSize: pageSize
    }
  })
}