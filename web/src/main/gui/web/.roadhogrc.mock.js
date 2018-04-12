import { delay } from 'roadhog-api-doc';
const apis = {
  'POST /public/api/login':'http://www.potafish.com',
  'GET /public/api/user/me':'http://www.potafish.com',
  'GET /public/api/user': 'http://www.potafish.com',
  'GET /public/api/users': {
    status: 200,
    data: {
        "data": [
          {
            "id": 12012,
            "name": "VIP用户",
            "status": "在职",
            "phone": "180-1205-3301",
            "email": "gdasad@das.com"
          },
          {
            "id": 12018,
            "name": "李白",
            "status": "在职",
            "phone": "180-1200-3301",
            "email": "dasdas@das.com"
          },
          {
            "id": 13012,
            "name": "Alice",
            "status": "离职",
            "phone": "180-1205-3201",
            "email": "psdadkl@das.com"
          }
        ],
        "page": 0,
        "size": 0,
        "totalElements": 0,
        "totalPage": 0
      },
  },
  'GET /public/api/user/*': {
    status: 200,
    data:{
      id: 1,
      name: '测试数据',
      phone: '187-4131-4569',
      email: 'dasda@dad.com',
      status: '在职'
    }
  },
  'PUT /public/api/user': {
    status: 200,
  },
  'POST /public/api/user': {
    status: 200,
    data: {id: 8}
  },

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
