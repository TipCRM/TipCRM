import { delay } from 'roadhog-api-doc';

const apis = {
  'POST /public/api/login':'http://www.potafish.com',
  'GET /public/api/user/me':'http://www.potafish.com',
  'POST /public/api/my/customers' :'http://www.potafish.com',
  'POST /public/api/notification/my' : 'http://www.potafish.com',
  'GET /public/api/roles' : 'http://www.potafish.com',
  'GET /public/api/permission/role/*': 'http://www.potafish.com',
  'GET /public/api/menu' : {status: 200, data:[
    {title:'客户管理',name:'CUSTOMER', active: true, children:[
      {title:'我的所有客户', id: 1, name: 'MY_CUSTOMER', active: true,
        children:[
          {title:'客户资料', name:'CUSTOMER_INFO', id:1 , active: true},
          {title:'联系人信息', name:'CUSTOMER_CONTACT', id:2},
          {title:'跟单记录', name:'CUSTOMER_CONTRACT', id:3},
          {title:'订单记录', id:4},
          {title:'操作记录', id:5}]},
      {title:'共享的客户', id: 2 ,name: 'MY_CUSTOMERS', content: 'coding'}]},
    {title:'通知中心',name:'NOTIFICATION', content:"维护中...", active: true, children:[
      {title: '客户操作通知', id:1, name: 'CUSTOMER_NOTIFICATION', active: true},
      {title: '审核通知', id:2, content:'开发中...'},]},
    {title:'跟单管理', content:"维护中...", children:[]},
    {title:'订单管理', content:"维护中..."},
    {title:'财务管理', content:"维护中..."},
    {title:'员工管理', content:"维护中..."},
    {title:'产品管理', content:"维护中..."},
    {title:'角色&权限',content:"维护中...", active: true, name:'ROLE_PERMISSION', children:[
      {title:'角色管理',name:'ROLE_PERMISSION', active: true, id: 1},
      {title:'权限管理', id: 2},
    ]},
    {title:'客户公海', content:"维护中..."},
    {title:'报表管理', content:"维护中..."},
    {title:'安全退出', content:"维护中..."},
    {title:'安全退出Test'},]},
  'GET /public/api/menus' :'http://www.potafish.com',
};

export default delay(apis, 1000);
