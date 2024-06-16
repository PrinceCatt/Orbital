import request from '@/utils/request'

//{section, (pageNum)}
export function getPost(section) {
  return request({
    url: '/post/section',
    method: 'get',
    params: {section}
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

