import request from '@/utils/request'

// 查询厂区列表
export function listFactoryArea(query) {
  return request({
    url: '/industry/factoryArea/list',
    method: 'get',
    params: query
  })
}

// 查询厂区详细
export function getFactoryArea(id) {
  return request({
    url: '/industry/factoryArea/' + id,
    method: 'get'
  })
}

// 新增厂区
export function addFactoryArea(data) {
  return request({
    url: '/industry/factoryArea',
    method: 'post',
    data: data
  })
}

// 修改厂区
export function updateFactoryArea(data) {
  return request({
    url: '/industry/factoryArea',
    method: 'put',
    data: data
  })
}

// 删除厂区
export function delFactoryArea(id) {
  return request({
    url: '/industry/factoryArea/' + id,
    method: 'delete'
  })
}
