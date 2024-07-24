import request from '@/utils/request'

export function getComments(postId) {
    return request({
      url:'post/comment',
      method: 'get',
      params: {postId}
    })
  }

export function like(commentId) {
    return request({
      url: '/userlike/like',
      method: 'post',
      params: {commentId}
    })
  }

  export function unlike(commentId) {
    return request({
      url: '/userlike/unlike',
      method: 'post',
      params: {commentId}
    })
  }

  export function replyComment(data) {
    return request({
      url: '/post/comment/new',
      method: 'post',
      data
    })
  }

  export function getStatus(commentId) {
    return request({
      url: '/userlike/status',
      method: 'get',
      params: {commentId}
    })
  }