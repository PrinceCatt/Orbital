export function getComments(postId) {
    return request({
      url:'post/comment',
      method: 'get',
      params: {postId}
    })
  }

export function like(parentComment, id) {
    return request({
      url: '/post/section',
      method: 'get',
      params: {section, id}
    })
  }

  export function getPost(section, id) {
    return request({
      url: '/post/section',
      method: 'get',
      params: {section, id}
    })
  }

  export function getPost(section, id) {
    return request({
      url: '/post/section',
      method: 'get',
      params: {section, id}
    })
  }