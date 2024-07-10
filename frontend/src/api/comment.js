import request from '@/utils/request'

export function getComments(postId) {
    return request({
      url:'post/comment',
      method: 'get',
      params: {postId}
    })
  }

export function like(comment) {
    return request({
      url: '/userlike/like',
      method: 'post',
      params: {comment}
    })
  }

  export function unlike(comment) {
    return request({
      url: '/userlike/unlike',
      method: 'post',
      params: {comment}
    })
  }

  export function reply(comment, token) {
    return request({
      url: '/post/comment/new',
      method: 'post',
      params: {comment, token}
    })
  }

