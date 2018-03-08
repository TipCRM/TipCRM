import { stringify } from 'qs';
import request from '../utils/request';

/**
 * the common api used by the routes
 * @returns {Object}
 */
export async function queryProjectNotice() {
  return request('/api/project/notice');
}

export async function queryActivities() {
  return request('/api/activities');
}

export async function queryRule(params) {
  return request(`/api/rule?${stringify(params)}`);
}

export async function removeRule(params) {
  return request('/api/rule', {
    method: 'POST',
    body: {
      ...params,
      method: 'delete',
    },
  });
}

export async function addRule(params) {
  return request('/api/rule', {
    method: 'POST',
    body: {
      ...params,
      method: 'post',
    },
  });
}

export async function fakeSubmitForm(params) {
  return request('/api/forms', {
    method: 'POST',
    body: params,
  });
}

export async function fakeChartData() {
  return request('/api/fake_chart_data');
}

export async function queryTags() {
  return request('/api/tags');
}

export async function queryBasicProfile() {
  return request('/api/profile/basic');
}

export async function queryAdvancedProfile() {
  return request('/api/profile/advanced');
}

export async function queryFakeList(params) {
  return request(`/api/fake_list?${stringify(params)}`);
}

/** api for Tip crm **/
export async function login(params) {
  return request('/public/api/login', {
    method: 'POST',
    body: params,
  });
}

export async function logout(){
  return request("/public/api/logout");
}

export async function register(params) {
  return request('/public/api/regist', {
    method: 'POST',
    body: params,
  });
}

export async function queryNotices(params) {
  return request('/public/api/notification/my',{
    method:'POST',
    body:params,
  });
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

export async function queryMenu() {
  return request('/public/api/menu');
}

export async function queryCustomerStatus(){
  return request("/public/api/type/customerStatus");
}
