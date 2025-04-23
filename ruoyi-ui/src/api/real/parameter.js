import request from '@/utils/request'

// 当前页面数据
export function slotSingleParam(data) {
  return request({
    url: '/industry/SlotSingleParam/list',
    method: 'POST',
    data: data
  })
}

// 当前页面数据修改
export function slotSingleParamEdit(data) {
  return request({
    url: '/industry/SlotSingleParam/edit',
    method: 'POST',
    data: data
  })
}

// 修改参数记录查询页面
export function paramModifyList(data) {
  return request({
    url: '/industry/paramModify/list',
    method: 'POST',
    data: data
  })
}

// 获取当前用户厂区集合
export function factoryArea() {
  return request({
    url: '/industry/SlotSingleParam/list',
    method: 'GET',
  })
}

// 电解槽槽龄设置
export function potAgeList(data) {
  return request({
    url: '/industry/potAge/list',
    method: 'POST',
    data: data
  })
}

// 电解槽槽龄修改
export function potAgeEdit(data) {
  return request({
    url: '/industry/potAge/edit',
    method: 'POST',
    data: data
  })
}
