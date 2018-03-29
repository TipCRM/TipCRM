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
  return request('/public/api/menu');
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
