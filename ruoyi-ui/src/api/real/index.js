import request from '@/utils/request'

// 获取当前用户厂区集合
export function factoryArea() {
  return request({
    url: '/industry/factoryArea/user',
    method: 'GET',
  })
}

// 实时数据页面
export function displayList(data) {
  return request({
    url: '/realtime/display/list',
    method: 'POST',
    data: data
  })
}

// 工作电压 （新增）
export function displayVolt(data) {
  return request({
    url: '/realtime/display/volt',
    method: 'POST',
    data: data
  })
}

// 测量数据输入界面
export function slotOffLineParam(data) {
  return request({
    url: '/industry/SlotOffLineParam/list',
    method: 'POST',
    data: data
  })
}

// 测量数据输入修改
export function slotOffLineParamEdit(data) {
  return request({
    url: '/industry/SlotOffLineParam/edit',
    method: 'POST',
    data: data
  })
}

// 测量数据显示界面
export function slotOffLineCheckbox(data) {
  return request({
    url: '/industry/SlotOffLineParamChart/checkbox',
    method: 'POST',
    data: data
  })
}

// AE信息列表 列表查询
export function aeInfoList(data) {
  return request({
    url: '/industry/aeInfo/list',
    method: 'POST',
    data: data
  })
}

// AE信息列表 图表数据
export function slotOffChart(data) {
  return request({
    url: '/industry/SlotOffLineParamChart/chart',
    method: 'POST',
    data: data
  })
}

// 配置信息查询
export function refreshConfig() {
  return request({
    url: '/industry/refreshConfig/user',
    method: 'GET',
  })
}

// 获取全部厂区集合
export function factoryAreaAll() {
  return request({
    url: '/industry/factoryArea/all',
    method: 'GET',
  })
}

// 全部厂号集合
export function factoryAreaFactory() {
  return request({
    url: '/industry/factoryArea/factory',
    method: 'GET',
  })
}

// 配置信息
export function refreshConfigEdit(data) {
  return request({
    url: '/industry/refreshConfig/edit',
    method: 'POST',
    data: data
  })
}
