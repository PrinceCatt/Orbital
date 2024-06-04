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

// {name, token}
export function updateName(data) {
  return request({
    url: '/user/updateName',
    method: 'post',
    data
  })
}

// {avatar, token}
export function updateAvatar(data) {
  return request({
    url: '/user/updateAvatar',
    method: 'put',
    data
  })
}