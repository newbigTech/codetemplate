import request from '@/utils/request'

export function fetchSysUserList(query) {
  return request({
    url: '/api/v1/sysUser/list?' + query,
    method: 'get'
  })
}

export function fetchSysUserRoleList(query) {
  return request({
    url: '/api/v1/sysUserRole/list?' + query,
    method: 'get'
  })
}
export function fetchSysDictList(query) {
  return request({
    url: '/api/v1/sysDict/list?' + query,
    method: 'get'
  })
}
export function addSysDict(data) {
  return request({
    url: '/api/v1/sysDict/add',
    method: 'post',
    data: data
  })
}
export function updateSysDict(data) {
  return request({
    url: '/api/v1/sysDict/update',
    method: 'post',
    data: data
  })
}
export function deleteSysDict(id) {
  return request({
    url: '/api/v1/sysDict/delete',
    method: 'post',
    data: {
      id: id
    }
  })
}

export function getSysOrgTree(query) {
  return request({
    url: '/api/v1/sysOrg/list?' + query,
    method: 'get'
  })
}

export function addSysOrg(data) {
  return request({
    url: '/api/v1/sysOrg/add',
    method: 'post',
    data: data
  })
}
export function updateSysOrg(data) {
  return request({
    url: '/api/v1/sysOrg/update',
    method: 'post',
    data: data
  })
}
