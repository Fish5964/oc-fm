import request from '@/utils/requestReport'

export function loadTable(data) {
  return request({
    url: '/dataset/table/list',
    method: 'post',
    loading: true,
    data
  })
}

export function getScene(sceneId) {
  return request({
    url: '/dataset/group/getScene/' + sceneId,
    loading: true,
    method: 'post'
  })
}

export function addGroup(data) {
  return request({
    url: '/dataset/group/save',
    method: 'post',
    loading: true,
    data
  })
}

export function delGroup(groupId) {
  return request({
    url: '/dataset/group/delete/' + groupId,
    loading: true,
    method: 'post'
  })
}

export function addTable(data) {
  return request({
    url: '/dataset/table/update',
    method: 'post',
    loading: true,
    data
  })
}

export function alter(data) {
  return request({
    url: '/dataset/table/alter',
    method: 'post',
    loading: true,
    data
  })
}

export function delTable(tableId) {
  return request({
    url: '/dataset/table/delete/' + tableId,
    loading: true,
    method: 'post'
  })
}

export function groupTree(data) {
  return request({
    url: '/dataset/group/tree',
    method: 'post',
    loading: true,
    data
  })
}

export function listDatasource() {
  return request({
    url: '/datasource/list',
    loading: true,
    method: 'get'
  })
}

export function getTable(id, hideMsg = false) {
  return request({
    url: '/dataset/table/get/' + id,
    loading: false,
    method: 'post',
    hideMsg: hideMsg
  })
}

export function getPreviewData(data) {
  return request({
    url: '/dataset/table/getPreviewData',
    method: 'post',
    loading: true,
    data
  })
}

export function fieldList(id, showLoading = true) {
  return request({
    url: '/dataset/field/list/' + id,
    loading: showLoading,
    method: 'post'
  })
}

export function fieldListDQ(id, showLoading = true) {
  return request({
    url: '/dataset/field/listByDQ/' + id,
    loading: showLoading,
    method: 'post'
  })
}

export function batchEdit(data) {
  return request({
    url: '/dataset/field/batchEdit',
    method: 'post',
    loading: true,
    data
  })
}

export function post(url, data, showLoading = true, timeout = 60000) {
  return request({
    url: url,
    method: 'post',
    loading: showLoading,
    data
  })
}

export function fieldValues(fieldId) {
  return request({
    url: '/dataset/field/fieldValues/' + fieldId,
    method: 'post',
    loading: true
  })
}

export function multFieldValues(data) {
  return request({
    url: '/dataset/field/multFieldValues',
    method: 'post',
    loading: true,
    data
  })
}

export function linkMultFieldValues(data) {
  return request({
    url: '/dataset/field/linkMultFieldValues',
    method: 'post',
    loading: true,
    data
  })
}

export function isKettleRunning(showLoading = true) {
  return request({
    url: '/dataset/group/isKettleRunning',
    method: 'post',
    loading: showLoading
  })
}

export function taskList(spage, size, data) {
  return request({
    url: '/dataset/group/isKettleRunning',
    method: 'post',
    // eslint-disable-next-line no-undef
    loading: showLoading
  })
}

export function datasetTaskList(page, size, data, loading) {
  return request({
    url: '/dataset/task/pageList/' + page + '/' + size,
    method: 'post',
    data,
    loading: loading
  })
}

export function datasetRowPermissionsList(datasetId, page, size, data, loading) {
  return request({
    url: 'plugin/dataset/rowPermissions/pageList/' + datasetId + '/' + page + '/' + size,
    method: 'post',
    data,
    loading: loading
  })
}

export function checkCustomDs() {
  return request({
    url: '/system/checkCustomDs',
    method: 'post',
    loading: true
  })
}

export default { loadTable, getScene, addGroup, delGroup, addTable, delTable, groupTree, checkCustomDs }
