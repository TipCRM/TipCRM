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
export function userLogout(){
  return request('/public/api/logout');
}

// user api
export async function fetchCurrentUser(){
  return request('/public/api/user/me');
}
export async function fetchUserByName(params) {
  return request('/public/api/user?userName='+params.userName+"&includeDismiss="+params.includeDismiss);
}
export async function createNewUser(params){
  return request('/public/api/user', {
    method: 'POST',
    body: params,
  });
}
export async function fetchUserDetailInfo(params){
  return request('/public/api/user/'+params.userId);
}
export async function disMissUser(params){
  return request('/pubic/api/user/dismiss', {
    method: 'DELETE',
    body: params,
  });
}
export async function updateUserInfo(params){
  return request('/public/api/user', {
    method: 'PUT',
    body: params,
  });
}
export async function fetchDepartmentUsers(){
  return request('/public/api/users');
}

// menu api
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

// permission api
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
export async function listMyPermissions(){
  return request('/public/api/permission/me');
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

// department
export async function fetchAllDepartments(){
  return request('/public/api/departments');
}
export async function createNewDepartment(params){
  return request('/public/api/department',{
    method: 'POST',
    body: params,
  });
}
export async function updateDepartment(params){
  return request('/public/api/department',{
    method: 'PUT',
    body: params,
  });
}
export async function deletDepartment(params) {
  return request('/public/api/department/'+params.deleteId, {
    method: 'DELETE'
  });
}
