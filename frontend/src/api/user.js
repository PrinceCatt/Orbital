import request from '@/utils/request'
/* 
{   email:123456
    password:xxxxxx
}
*/
export function register(data) {
  return request({
    url: '/user/register',
    method: 'post',
    data
  })
}


export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

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

export function updateName(data) {
  return request({
    url: '/user/updateName',
    method: 'post',
    data
  })
}

export function updateAvatar(data) {
  return request({
    url: '/user/updateAvatar',
    method: 'post',
    data
  })
}