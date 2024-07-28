import request from '@/utils/request'


//register
// {name, email, password}
export function register(data) {
  return request({
    url: '/user/register',
    method: 'post',
    data
  })
}

//login
// {email, password}
export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

//to get user information
// {token}
export function getInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}

//logout
export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}

//update username
// {newName}
export function updateName(name) {
  return request({
    url: '/user/updateName',
    method: 'post',
    params: { name }
  })
}

// {newAvatar}
// Not using for user's updating avatar
export function updateAvatar(avatar) {
  return request({
    url: '/user/updateAvatar',
    method: 'post',
    params: { avatar }
  })
}

//to get all posts of user's own
// {pageNum}
export function myPosts(pageNum) {
  return request({
    url: '/user/post',
    method: 'get',
    params: {pageNum}
  })
}

//to delete a certain post of user's own
//{postId}
export function deletePost(postId) {
  return request({
    url: '/user/post/delete',
    method: 'delete',
    params: {postId}
  })
}

//to add a postId to browse
export function addHistory(postId) {
  return request({
    url: '/user/post/addHistory',
    method: 'post',
    params: {postId}
  })
}

export function getHistory(pageNum) {
  return request({
    url: '/user/post/getHistory',
    method: 'get',
    params: {pageNum}
  })
}
