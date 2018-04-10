import { delay } from 'roadhog-api-doc';
const noProxy = true;
const apisNoProxy ={
  'GET /public/api/user/me':{
    status: 200,
    data: {userName: '李白'}
  },
  'GET /public/api/menu/me' : {status: 200, data:[
    {title:'客户管理',name:'CUSTOMER_MANAGEMENT', active: true, children:[
      {title:'我的所有客户', id: 1, name: 'MY_CUSTOMER', active: true,
        children:[
          {title:'客户资料', name:'CUSTOMER_INFO', id:1 , active: true},
          {title:'联系人信息', name:'CUSTOMER_CONTACT', id:2},
          {title:'跟单记录', name:'CUSTOMER_CONTRACT', id:3},
          {title:'订单记录', id:4},
          {title:'操作记录', id:5}]},
      {title:'共享的客户', id: 2 ,name: 'MY_CUSTOMERS', content: 'coding'}]},
    {title:'通知中心',name:'NOTIFICATION_CENTER', content:"维护中...", active: true, children:[
      {title: '客户操作通知', id:1, name: 'CUSTOMER_NOTIFICATION', active: true},
      {title: '审核通知', id:2, content:'开发中...'},]},
    {title:'跟单管理', content:"维护中...", children:[]},
    {title:'订单管理', content:"维护中..."},
    {title:'财务管理', content:"维护中..."},
    {title:'员工管理', content:"维护中..."},
    {title:'产品管理', content:"维护中..."},
    {title:'角色&权限',content:"维护中...", active: true, name:'ROLE_AND_PERMISSION', children:[
      {title:'角色管理',name:'ROLES', active: true, id: 1},
      {title:'权限管理', id: 2},
    ]},
    {title:'客户公海', content:"维护中..."},
    {title:'报表管理', content:"维护中..."},
    {title:'安全退出', content:"维护中..."},
    {title:'安全退出Test'},]},
    'GET /public/api/permission/menu/*': {
      status: 200,
      data: ['role:add', 'role:update', 'role:delete']
    },
    'GET /public/api/roles' : {status: 200, data:[
      {id: 1, displayName: '经理', entryUser: '系统', entryDatetime:'2017-8-17 18:42:00'},
      {id: 2, displayName: '总裁', entryUser: '系统', entryDatetime:'2017-8-17 18:42:00'},
      {id: 3, displayName: '员工', entryUser: '系统', entryDatetime:'2017-8-17 18:42:00'},
    ]},
    'GET /public/api/permission/role/*': {
      status: 200,
      data:[
        {name: '客户管理', permissions:[
          {displayName:'添加客户', checked: true},
          {displayName:'修改客户', checked: false},
          {displayName:'删除客户', checked: true},
        ]},
        {name: '客户角色', permissions:[
          {displayName:'添加角色', checked: true},
          {displayName:'修改角色', checked: false},
          {displayName:'删除角色', checked: true},
        ]}
      ]
    },
    'POST /public/api/role': {
      status: 200,
      data:{id:1}
    },
    'PUT /public/api/permission/role/*': {
      status: 200,
    },
    'PUT /public/api/role': {
      status: 200,
    },
    'DELETE /public/api/role/*': {
      status: 200
    },
    'GET /public/api/departments':{
      status: 200,
      data:[
        {
          "entryTime": "2018-04-03T03:06:21.182Z",
          "entryUser": "string",
          "id": 0,
          "manager": {
            "id": 0,
            "name": "string"
          },
          "name": "string",
          "total": 0
        },
        {
          "entryTime": "2018-04-03T03:06:21.182Z",
          "entryUser": "string",
          "id": 0,
          "manager": {
            "id": 0,
            "name": "string"
          },
          "name": "string",
          "total": 0
        }
      ]
    },
  'POST /public/api/department': {
    status: 200,
    data:[]
  },
  'PUT /public/api/department': {
    status: 200,
    data:[]
  },
  'DELETE /public/api/department/*': {
    status: 200,
    data:[]
  },
  'GET /public/api/user': {
    status: 200,
    data: [
      {id: 1, name: '李白'},
      {id: 2, name: '鲁班七号'}],
  },
  'POST /public/api/user': {
    status: 200,
    data: {id: 11}
  },
  'GET /public/api/user/*': {
    status: 200,
    data: {
      userName:'李白',
      email:'gdsy@dasd.com',
      idCard:'--',
      birthday:'2017-08-12',
      phoneNo:'18012045894',
      avatar:'',
      levelId:1,
      levelName:'新员工',
      payment: 20,
      motto:'开心开心',
      status: '在职',
      hirer:' 李白',
      hirerTime:'2017-12-07',
      departmentId: 1,
      departmentName: '销售一部',
      dismissUser:'',
      dismissDate:'',
      dismissReason:'',
    }
  },
  'DELETE /pubic/api/user/dismiss': {
    status: 200,
    data: []
  },
  'PUT public/api/user': {
    status: 200,
    data: []
  },
  'GET /public/api/users': {
    "data": {
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
    "languageLocale": "US",
    "message": "string",
    "requestId": "5a124806f9f9e98924014e75",
    "status": 200
  }
}

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

export default delay( noProxy ? apis : apisNoProxy, 1000);
