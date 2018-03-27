import fetch from 'dva/fetch';
import store from '../index';
import {routerRedux} from 'dva/router';
import  {NotificationUtil} from 'util';
import {notification} from 'antd';

function parseJSON(response) {
  return response.json();
}

function checkStatus(response){
  if (response.status < 200 || response.status >= 500){
    const content = (<div>错误代码：{response.status} <br/> 错误原因：{response.message}</div>);
    notification.error({message:'System Error', description: content, duration:0});
  }
  return response;
}

/**
 * Requests a URL, returning a promise.
 *
 * @param  {string} url       The URL we want to request
 * @param  {object} [options] The options we want to pass to "fetch"
 * @return {object}           An object containing either "data" or "err"
 */
export default function request(url, options) {
  const defaultOptions = {
    credentials: 'include',
  };
  const newOptions = { ...defaultOptions, ...options };
  if (newOptions.method === 'POST' || newOptions.method === 'PUT') {
    newOptions.headers = {
      Accept: 'application/json',
      'Content-Type': 'application/json; charset=utf-8',
      ...newOptions.headers,
    };
    newOptions.body = JSON.stringify(newOptions.body);
  }

  return fetch(url, newOptions)
    .then(parseJSON)
    .then(checkStatus)
    .catch(err => ({}));
}
