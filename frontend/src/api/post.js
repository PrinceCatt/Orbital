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

