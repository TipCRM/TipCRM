import { delay } from 'roadhog-api-doc';
const apis = {
  'POST /public/api/login':'http://www.potafish.com',
  'GET /public/api/logout':'http://www.potafish.com',
  'GET /public/api/user/me':'http://www.potafish.com',
  'GET /public/api/user': 'http://www.potafish.com',
  'POST /public/api/user/query': 'http://www.potafish.com',

  'POST /public/api/my/customers' :'http://www.potafish.com',
  'POST /public/api/notification/my' : 'http://www.potafish.com',
  'GET /public/api/roles' : 'http://www.potafish.com',
  'GET /public/api/permission/role/*': 'http://www.potafish.com',
  'PUT /public/api/permission/role/*': 'http://www.potafish.com',
  'GET /public/api/permission/me': 'http://www.potafish.com',

  'POST /public/api/role': 'http://www.potafish.com',
  'PUT /public/api/role': 'http://www.potafish.com',
  'DELETE /public/api/role/*': 'http://www.potafish.com',
  'POST /public/api/cache/refresh/roleAndPermission': 'http://www.potafish.com',
  'GET /public/api/menu/me' :'http://www.potafish.com',
  'GET /public/api/permission/menu/*': 'http://www.potafish.com',
  'GET /public/api/departments': 'http://www.potafish.com',
  'GET /public/api/departments': 'http://www.potafish.com',
  'POST /public/api/department': 'http://www.potafish.com',
  'PUT /public/api/department': 'http://www.potafish.com',
  'DELETE /public/api/department/*': 'http://www.potafish.com',
};

export default delay(apis, 1000);
