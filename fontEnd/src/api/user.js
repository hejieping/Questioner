/**
 * Created by qi on 2017/10/18.
 */
import fetch from '@/utils/fetch'

export function getUser (userId) {
  return fetch({
    url: '/user/' + userId,
    method: 'get'
  })
}

export function getUserPreference (userId, size = 5) {
  return fetch({
    url: '/user/getPreference/' + userId,
    methods: 'get',
    params: {
      preferenceSize: size
    }
  })
}

export function getFollowInfo (userId) {
  return fetch({
    url: '/user/followersInfo/' + userId,
    method: 'get'
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
    method: 'get'
  })
}

export function followUser (followedId) {
  return fetch({
    url: '/user/follow/' + followedId,
    method: 'get'
  })
}

export function unFollowUser (followedId) {
  return fetch({
    url: '/user/unFollow/' + followedId,
    method: 'get'
  })
}

export function getUserFollowCourse (userId, currentPage, pageSize) {
  return fetch({
    url: '/questionType/getFollow/' + userId,
    method: 'get',
    params: {
      currentPage: currentPage,
      pageSize: pageSize
    }
  })
}

export function getUserFollowQuestion (userId, currentPage, pageSize) {
  return fetch({
    url: '/question/getFollow/' + userId,
    method: 'get',
    params: {
      currentPage: currentPage,
      pageSize: pageSize
    }
  })
}

export function getUserQuestionByViews (userId, currentPage, pageSize) {
  return fetch({
    url: '/question/getUserQuestionByViews/' + userId,
    method: 'get',
    params: {
      currentPage: currentPage,
      pageSize: pageSize
    }
  })
}

export function getUserQuestionByDateTime (userId, currentPage, pageSize) {
  return fetch({
    url: '/question/getUserQuestionByDateTime/' + userId,
    method: 'get',
    params: {
      currentPage: currentPage,
      pageSize: pageSize
    }
  })
}

export function getUserAnswerByDateTime (userId, currentPage, pageSize) {
  return fetch({
    url: '/answer/getUserAnswersByDateTime/' + userId,
    method: 'get',
    params: {
      currentPage: currentPage,
      pageSize: pageSize
    }
  })
}

export function getUserAnswersByThumbsUpCount (userId, currentPage, pageSize) {
  return fetch({
    url: '/answer/getUserAnswersByThumbsUpCount/' + userId,
    method: 'get',
    params: {
      currentPage: currentPage,
      pageSize: pageSize
    }
  })
}

export function saveProfile (profile) {
  return fetch({
    url: '/user/profile/saveProfile',
    method: 'post',
    data: profile
  })
}
