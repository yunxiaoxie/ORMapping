import request from '@/utils/request'
import qs from 'qs'

export function fetchList(query) {
  return request({
    url: '/course/list',
    method: 'get',
    params: query
  })
}

export function fetchNames() {
  return request({
    url: '/course/names',
    method: 'get'
  })
}

export function fetchCourse(id) {
  return request({
    url: '/course/detail',
    method: 'get',
    params: { id }
  })
}

export function fetchPv(pv) {
  return request({
    url: '/course/pv',
    method: 'get',
    params: { pv }
  })
}

export function createCourse(data) {
  return request({
    url: '/course/addOrUpd',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function createCourseChapter(data) {
  return request({
    url: '/course/chapter/addOrUpd',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function updateCourse(data) {
  return request({
    url: '/course/addOrUpd',
    method: 'post',
    data: qs.stringify(data)
  })
}
