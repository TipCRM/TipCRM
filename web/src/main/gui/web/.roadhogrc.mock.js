import { delay } from 'roadhog-api-doc';
const apis = {
  // user
  'POST /public/api/login':'http://tipcrm.potafish.com',
  'GET /public/api/logout':'http://tipcrm.potafish.com',
  'GET /public/api/user/me':'http://tipcrm.potafish.com',
  'GET /public/api/user': 'http://tipcrm.potafish.com',
  'POST /public/api/user/query': 'http://tipcrm.potafish.com',
  'PUT /public/api/user/department': 'http://tipcrm.potafish.com',
  'PUT /public/api/user/password': 'http://tipcrm.potafish.com',
  'PUT /public/api/user/me': 'http://tipcrm.potafish.com',
  'PUT /public/api/user/level': 'http://tipcrm.potafish.com',

  // permissions
  'POST /public/api/my/customers' :'http://tipcrm.potafish.com',
  'POST /public/api/notification/my' : 'http://tipcrm.potafish.com',
  'GET /public/api/roles' : 'http://tipcrm.potafish.com',
  'GET /public/api/permission/role/*': 'http://tipcrm.potafish.com',
  'PUT /public/api/permission/role/*': 'http://tipcrm.potafish.com',
  'GET /public/api/permission/me': 'http://tipcrm.potafish.com',

  // attachment
  'POST /public/avatar' : 'http://tipcrm.potafish.com',

  // role
  'POST /public/api/role': 'http://tipcrm.potafish.com',
  'PUT /public/api/role': 'http://tipcrm.potafish.com',
  'DELETE /public/api/role/*': 'http://tipcrm.potafish.com',

  // cache
  'POST /public/api/cache/refresh/roleAndPermission': 'http://tipcrm.potafish.com',

  // menu
  'GET /public/api/menu/me' :'http://tipcrm.potafish.com',
  'GET /public/api/permission/menu/*': 'http://tipcrm.potafish.com',

  // departments
  'GET /public/api/departments': 'http://tipcrm.potafish.com',
  'GET /public/api/departments': 'http://tipcrm.potafish.com',
  'POST /public/api/department': 'http://tipcrm.potafish.com',
  'PUT /public/api/department': 'http://tipcrm.potafish.com',
  'DELETE /public/api/department/*': 'http://tipcrm.potafish.com',

  //utils
  'GET /public/api/mail':'http://tipcrm.potafish.com',

  // level
  'GET /public/api/levels': 'http://tipcrm.potafish.com',
};

export default delay(apis, 1000);
