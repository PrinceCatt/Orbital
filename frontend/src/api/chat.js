import request from '@/utils/request'

export function getMessages(toUid) {
    return request({
      url:'message/history',
      method: 'get',
      params: {toUid}
    })
  }