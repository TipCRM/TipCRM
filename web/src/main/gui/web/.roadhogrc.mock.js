import { delay } from 'roadhog-api-doc';
const apis = {
  // user
  'POST /public/api/login':'http://www.potafish.com',
  'GET /public/api/logout':'http://www.potafish.com',
  'GET /public/api/user/me':'http://www.potafish.com',
  'GET /public/api/user': 'http://www.potafish.com',
  'POST /public/api/user/query': 'http://www.potafish.com',
  'PUT /public/api/user/department': 'http://www.potafish.com',
  'PUT /public/api/user/password': 'http://www.potafish.com',
  'PUT /public/api/user/me': 'http://www.potafish.com',

  // permissions
  'POST /public/api/my/customers' :'http://www.potafish.com',
  'POST /public/api/notification/my' : 'http://www.potafish.com',
  'GET /public/api/roles' : 'http://www.potafish.com',
  'GET /public/api/permission/role/*': 'http://www.potafish.com',
  'PUT /public/api/permission/role/*': 'http://www.potafish.com',
  'GET /public/api/permission/me': 'http://www.potafish.com',

  // attachment
  'POST /public/avatar' : 'http://www.potafish.com',

  // role
  'POST /public/api/role': 'http://www.potafish.com',
  'PUT /public/api/role': 'http://www.potafish.com',
  'DELETE /public/api/role/*': 'http://www.potafish.com',

  // cache
  'POST /public/api/cache/refresh/roleAndPermission': 'http://www.potafish.com',

  // menu
  'GET /public/api/menu/me' :'http://www.potafish.com',
  'GET /public/api/permission/menu/*': 'http://www.potafish.com',

  // departments
  'GET /public/api/departments': 'http://www.potafish.com',
  'GET /public/api/departments': 'http://www.potafish.com',
  'POST /public/api/department': 'http://www.potafish.com',
  'PUT /public/api/department': 'http://www.potafish.com',
  'DELETE /public/api/department/*': 'http://www.potafish.com',

  //utils
  'GET /public/api/mail':'http://www.potafish.com',

  // level
  'GET /public/api/levels': 'http://www.potafish.com',
};

export default delay(apis, 1000);
