/**
 * Created by Administrator on 2018/3/20.
 */
import request from '../utils/request';
/**
 * user loign
 */
export function userLogin(params) {
  return request('/public/api/login', {
    method: 'POST',
    body: params,
  });
}

export async function fetchCurrentUser(){
  return request('/public/api/user/me');
}

export async function fakeGetMenu(){
  return request('/public/api/menu/me');
}

export async function queryMyCustomers(params) {
  return request('/public/api/my/customers',{
    method: 'POST',
    body: params,
  });
}

export async function createCustomer(params){
  return request('/public/api/customer',{
    method: 'POST',
    body: params,
  });
}

export async function transferOutCustomer(params){
  return request('/public/api/customer/transfer/out',{
    method: 'POST',
    body: params,
  });
}

export async function fetchMyNotifictions(params){
  return request('/public/api/notification/my',{
    method:'POST',
    body: params,
  });
}

export async function listRoles(){
  return request('/public/api/roles');
}

export async function listRolePermissions(params){
  return request('/public/api/permission/role/'+params.id);
}

export async function changeRolePermissions(params) {
  return request('/public/api/permission/role/'+params.id,{
    method: 'PUT',
    body: params.permissions
  });
}

export async function getPermissionsByMenu(params){
  return request('/public/api/permission/menu/'+ params.menuId);
}

// the operation for role
export async function createNewRole(params){
  return request('/public/api/role',{
    method: 'POST',
    body: params,
  });
}

export async function updateRole(params) {
  return request('/public/api/role',{
    method: 'PUT',
    body: params,
  });
}

export async function deleteRole(params) {
  return request('/public/api/role/'+ params.deleteId,{
    method: 'DELETE'
  })
}

// cache
export async function flushRoleCache(){
  return request('/public/api/cache/refresh/roleAndPermission', {
    method: 'POST'
  });
}
