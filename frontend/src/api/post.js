import request from '@/utils/request'

//{section, (pageNum)}
export function getPost(data) {
  return request({
    url: '/post/section',
    method: 'get',
    params: data
  })
}

//{post:(section, title, content, time)}
export function newPost(data) {
  return request({
    url: '/user/post/new',
    method: 'post',
    data
  })
}

//{postId}
export function getPostbyId(postId) {
  return request({
    url: 'post/find/id',
    method: 'get',
    params: {postId}
  })
}


export function getComments(postId) {
  return request({
    url:'post/comment',
    method: 'get',
    params: {postId}
  })
}