import request from '@/utils/request'
/* 
{   email:123456
    password:xxxxxx
}
*/
export function login(data) {
  return request({
    url: 'http://localhost:8088/user/login', // to be modified
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
