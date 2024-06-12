//{section id, post id}
export function getPost(data) {
  return request({
    url: '/post/getpost',
    method: 'get',
    data
  })
}

//{}
export function newPost(data) {
  return request({
    url: '/post/section/find',
    method: 'post',
    data
  })
}