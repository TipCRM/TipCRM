import mockjs from 'mockjs';
import { getRule, postRule } from './mock/rule';
import { getActivities, getNotice, getFakeList } from './mock/api';
import { getFakeChartData } from './mock/chart';
import { imgMap } from './mock/utils';
import { getProfileBasicData } from './mock/profile';
import { getProfileAdvancedData } from './mock/profile';
import { getNotices } from './mock/notices';
import { format, delay } from 'roadhog-api-doc';

// 是否禁用代理
const noProxy = process.env.NO_PROXY === 'true';

// 代码中会兼容本地 service mock 以及部署站点的静态数据
const proxy = {
  // 支持值为 Object 和 Array
  'GET /api/currentUser': {
    $desc: "获取当前用户接口",
    $params: {
      pageSize: {
        desc: '分页',
        exp: 2,
      },
    },
    $body: {
      name: 'Brother Lu',
      avatar: '/favicon.png',
      userid: '00000001',
      notifyCount: 12,
    },
  },
  // GET POST 可省略
  'GET /api/users': [{
    key: '1',
    name: 'John Brown',
    age: 32,
    address: 'New York No. 1 Lake Park',
  }, {
    key: '2',
    name: 'Jim Green',
    age: 42,
    address: 'London No. 1 Lake Park',
  }, {
    key: '3',
    name: 'Joe Black',
    age: 32,
    address: 'Sidney No. 1 Lake Park',
  }],
  'GET /api/project/notice': getNotice,
  'GET /api/activities': getActivities,
  'GET /api/rule': getRule,
  'POST /api/rule': {
    $params: {
      pageSize: {
        desc: '分页',
        exp: 2,
      },
    },
    $body: postRule,
  },
  'POST /api/forms': (req, res) => {
    res.send({ message: 'Ok' });
  },
  'GET /api/tags': mockjs.mock({
    'list|100': [{ name: '@city', 'value|1-100': 150, 'type|0-2': 1 }]
  }),
  'GET /api/fake_list': getFakeList,
  'GET /api/fake_chart_data': getFakeChartData,
  'GET /api/profile/basic': getProfileBasicData,
  'GET /api/profile/advanced': getProfileAdvancedData,
  'POST /api/login/account': (req, res) => {
    const { password, userName, type } = req.body;
    if(password === '888888' && userName === 'admin'){
      res.send({
        status: 'ok',
        type,
        currentAuthority: 'admin'
      });
      return ;
    }
    if(password === '123456' && userName === 'user'){
      res.send({
        status: 'ok',
        type,
        currentAuthority: 'user'
      });
      return ;
    }
    res.send({
      status: 'error',
      type,
      currentAuthority: 'guest'
    });
  },
  'POST /api/register': (req, res) => {
    res.send({ status: 'ok', currentAuthority: 'user' });
  },
  'GET /api/notices': getNotices,
  'GET /api/500': (req, res) => {
    res.status(500).send({
      "timestamp": 1513932555104,
      "status": 500,
      "error": "error",
      "message": "error",
      "path": "/base/category/list"
    });
  },
  'GET /api/404': (req, res) => {
    res.status(404).send({
      "timestamp": 1513932643431,
      "status": 404,
      "error": "Not Found",
      "message": "No message available",
      "path": "/base/category/list/2121212"
    });
  },
  'GET /api/403': (req, res) => {
    res.status(403).send({
      "timestamp": 1513932555104,
      "status": 403,
      "error": "Unauthorized",
      "message": "Unauthorized",
      "path": "/base/category/list"
    });
  },
  'GET /api/401': (req, res) => {
    res.status(401).send({
      "timestamp": 1513932555104,
      "status": 401,
      "error": "Unauthorized",
      "message": "Unauthorized",
      "path": "/base/category/list"
    });
  },
};

const customerDatabase =[
  {key:1, customer:'华夏信息有限公司', contact:'黎明', phone:'183 0268 2233',lastContactTime:'2017-12-31', message:'下次联系',nextContactTime:'2018-1-1',customerStatus:'意向客户'},
  {key:2, customer:'远大启航有限公司', contact:'张伟', phone:'183 0268 3366',lastContactTime:'2017-1-31', message:'明天签约',nextContactTime:'2017-2-1',customerStatus:'签约客户'},
  {key:3, customer:'小窍门信息技术有限公司', contact:'李广飞', phone:'183 2032 3336',lastContactTime:'2017-6-1', message:'叫我不要再联系他了，改天我再试试，如果实在是没办法那就放弃',nextContactTime:'待定',customerStatus:'新客户'},
  {key:4, customer:'阿里巴巴集团', contact:'小马云', phone:'183 2032 3336',lastContactTime:'2017-6-1', message:'',nextContactTime:'待定',customerStatus:'新客户'},
  {key:5, customer:'Oracle', contact:'William Kenny', phone:'135 2202 2031',lastContactTime:'2016-6-1', message:'同意开始接触',nextContactTime:'2017-1-1',customerStatus:'新客户'},
  {key:6, customer:'广电网络', contact:'李明瑞', phone:'133 0074 3059',lastContactTime:'2017-6-1', message:'明天签单',nextContactTime:'2017-6-2',customerStatus:'过期客户'},
  {key:7, customer:'Microsoft Inc', contact:'Harry Potter', phone:'154 0015 3015',lastContactTime:'2015-6-1', message:'让我后天去签约，金额100万刀',nextContactTime:'2015-6-3',customerStatus:'过期客户'},
  {key:8, customer:'蚂蚁金服', contact:'Molly Gu', phone:'150 4456 5941',lastContactTime:'2018-2-1', message:'拜访他感觉良好，又继续的意思，需要再次联系一下。',nextContactTime:'2018-3-1',customerStatus:'新客户'},
];

const menuDatabase = [{
  name: '账户',
  icon: 'user',
  path: 'user',
  authority: 'guest',
  children: [{
    name: '登录',
    path: 'login',
  }, {
    name: '注册',
    path: 'register',
  }, {
    name: '注册结果',
    path: 'register-result',
  }],
},{
  name:'主页',
  icon:'home',
  path:'index',
},{
  name:'我的客户',
  icon:'team',
  path:'customer',
},{
  name:'销售报表',
  icon:'solution',
  path:'report',
},{
  name:'财务管理',
  icon:'pay-circle-o',
  path:'finance',
},{
  name:'资料库',
  icon:'file-text',
  path:'data',
},{
  name:'系统设置',
  icon:'setting',
  path:'system',
  children:[{
    name:'客户管理',
    icon:'usergroup-add',
    path:'customer',
  },
    {
      name:'员工管理',
      icon:'user',
      path:'employee',
    },
    {
      name:'审批管理',
      icon:'exception',
      path:'approve',
    },
    {
      name:'权限管理',
      icon:'lock',
      path:'permission',
    },
    {
      name:'财务管理',
      icon:'red-envelope',
      path:'finance',
    },{
      name:'资料管理',
      icon:'copy',
      path:'data',
    },],
},];

const apis = {
  'POST /public/api/login':'http://www.potafish.com',
  'POST /public/api/regist':'http://www.potafish.com',
  'GET /public/api/user/me':'http://www.potafish.com',
  'GET /api/currentUser': {
    $desc: "获取当前用户接口",
    $params: {
      pageSize: {
        desc: '分页',
        exp: 2,
      },
    },
    $body: {
      name: 'Brother Lu',
      avatar: '/favicon.png',
      userid: '00000001',
      notifyCount: 12,
    },
  },
  'GET /public/api/menu':{
    $body:menuDatabase,
  },
  'POST /public/api/my/customers':'http://www.potafish.com',
  'POST /public/api/customer': 'http://www.potafish.com',
};
export default noProxy ? delay(apis,1000) : delay(proxy, 1000);
