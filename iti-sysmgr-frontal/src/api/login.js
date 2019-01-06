import qs from 'qs'
import request from '@/utils/request'

export function loginByUsername(username, password) {
  const data = {
    account: username,
    password
  }
  return request({
    url: '/login',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function logout() {
  return request({
    url: '/login/logout',
    method: 'post'
  })
}

export function getUserInfo(token) {
  return request({
    url: '/user/info',
    method: 'get',
    params: { token }
  })
}

