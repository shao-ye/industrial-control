import request from '@/utils/request'

// 查询socket管理列表
export function listSocketInfo(query) {
  return request({
    url: '/industry/socketInfo/list',
    method: 'get',
    params: query
  })
}

// 查询socket管理详细
export function getSocketInfo(id) {
  return request({
    url: '/industry/socketInfo/' + id,
    method: 'get'
  })
}

// 新增socket管理
export function addSocketInfo(data) {
  return request({
    url: '/industry/socketInfo',
    method: 'post',
    data: data
  })
}

// 修改socket管理
export function updateSocketInfo(data) {
  return request({
    url: '/industry/socketInfo',
    method: 'put',
    data: data
  })
}

// 删除socket管理
export function delSocketInfo(id) {
  return request({
    url: '/industry/socketInfo/' + id,
    method: 'delete'
  })
}
