import request from '@/utils/request'

//{section, (pageNum)}
export function getPost(data) {
  return request({
    url: '/post/section',
    method: 'get',
    data
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