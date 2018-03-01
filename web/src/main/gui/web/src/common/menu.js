import { isUrl } from '../utils/utils';

/**
 *  menu data will be displayed
 * @type {*[]}
 */
const menuData = [{
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
  name:'客户管理',
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
  name:'个人中心',
  icon:'file-text',
  path:'user_center',
},{
  name:'系统设置',
  icon:'setting',
  path:'system',}
];

function formatter(data, parentPath = '', parentAuthority) {
  return data.map((item) => {
    let { path } = item;
    if (!isUrl(path)) {
      path = parentPath + item.path;
    }
    const result = {
      ...item,
      path,
      authority: item.authority || parentAuthority,
    };
    if (item.children) {
      result.children = formatter(item.children, `${parentPath}${item.path}/`, item.authority);
    }
    return result;
  });
}

export const getMenuData = () => formatter(menuData);
