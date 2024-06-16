import request from '@/utils/request'

// {name, email, password}
export function register(data) {
  return request({
    url: '/user/register',
    method: 'post',
    data
  })
}

// {email, password}
export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

// {token}
export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

// {newName}
export function updateName(name) {
  return request({
    url: '/user/updateName',
    method: 'post',
    params: { name }
  })
}

// {newAvatar}
export function updateAvatar(avatar) {
  return request({
    url: '/user/updateAvatar',
    method: 'post',
    params: {avatar}
  })
}

// {pageNum}
export function myPosts(pageNum) {
  return request({
    url: '/user/post',
    method: 'get',
    params: {pageNum}
  })
}

//{postId}
export function deletePost(postId) {
  return request({
    url: '/user/post/delete',
    method: 'delete',
    params: {postId}
  })
}