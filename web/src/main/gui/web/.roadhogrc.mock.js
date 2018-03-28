import { delay } from 'roadhog-api-doc';

const apis = {
  'POST /public/api/login':'http://www.potafish.com',
  'GET /public/api/user/me':'http://www.potafish.com',
  'POST /public/api/my/customers' :'http://www.potafish.com',
  'POST /public/api/notification/my' : 'http://www.potafish.com',
  'GET /public/api/menu' : {status: 200, data:[
    {title:'客户管理', content:"TEst",children:[
      {title:'我的所有客户', key: 1,
        children:[
          {title:'客户资料', key:1 },
          {title:'联系人信息', key:2},
          {title:'跟单记录', key:3},
          {title:'订单记录', key:4},
          {title:'操作记录', key:5}]},
      {title:'共享的客户', key: 2}]},
    {title:'通知中心', children:[
      {title: '客户操作通知', key:1},
      {title: '审核通知', key:2, content:'开发中...'},]},
    {title:'跟单管理', content:"维护中...", children:[]},
    {title:'订单管理'},
    {title:'财务管理'},
    {title:'员工管理'},
    {title:'产品管理'},
    {title:'角色&权限',content:"维护中...", children:[
      {title:'角色管理', key: 1},
      {title:'权限管理', key: 2},
    ]},
    {title:'客户公海'},
    {title:'报表管理'},
    {title:'安全退出'},
    {title:'安全退出Test'},]},
  'GET /public/api/menus' :'http://www.potafish.com',
};

export default delay(apis, 1000);
