import request from '@/utils/request'

//{section, (pageNum)}
export function getPost(section, pageNum) {
  return request({
    url: '/post/section',
    method: 'get',
    params: {section, pageNum}
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

//{post:(section, title, content, time)}
export function updatePost(data) {
  return request({
    url: '/user/post/update',
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


