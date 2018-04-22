/**
 * Created by mosesc on 03/28/18.
 */
import MyCustomerPanel from '../components/Panel/Customer/MyCustomerPanel';
import CustomerInfoCell from '../components/Panel/Customer/CustomerInfoCell';
import CustomerContactCell from '../components/Panel/Customer/CustomerContactCell';
import CustomerNotificationPanel from '../components/Panel/Notification/CustomerNotificationPanel';
import RoleAndPermissionPanel from '../components/Panel/RoleAndPermission/RoleAndPermissionPanel';
import MyRoleAndPermissionPanel from '../components/Panel/RoleAndPermission/MyRoleAndPermissionPanel';

import CompanyUserPanel from '../components/Panel/User/CompanyUserPanel';
import DepartmentUserPanel from '../components/Panel/User/CompanyUserPanel';

import DepartmentManagePanel from '../components/Panel/Department/DepartmentManagePanel';
import UserCenterPanel from '../components/Panel/UserCenter/UserCenterPanel';

import DynamicPanel from '../components/Common/DynamicPanel';
/**
 * the menu's component
 * @returns {*[]}
 */
export function menuComponentConstant(item){
  var component;
  const children = item.children;
  switch (item.name){
    case 'CUSTOMER_MANAGEMENT':
      component = (<DynamicPanel children={children} initChildrenPanel= {initCustomerComponent}/>);
      break;
    case 'NOTIFICATION_CENTER':
      component = (<DynamicPanel children={children} initChildrenPanel= {initNotificationComponent}/>);
      break;
    case 'ROLE_AND_PERMISSION':
      component = (<DynamicPanel children={children} initChildrenPanel= {initRoleAndPermissionComponent}/>);
      break;
    case 'USER_MANAGEMENT':
      component = (<DynamicPanel item={item} notTabs={true} initChildrenPanel={initUserManagementComponent}/>);
      break;
    case 'DEPARTMENT_MANAGEMENT':
      component = (<DynamicPanel item={item} notTabs={true} initChildrenPanel={initDepartmentManagementComponent}/>);
      break;
    case 'PERSONAL_CENTER':
      component = (<DynamicPanel item={item} notTabs={true} initChildrenPanel={initUserCenterComponent}/>);
      break;
    default:
      component = (<div>The page you request is not exist.</div>);
  }
  return component;
}

export function initCustomerComponent(item){
  var component;
  switch (item.name){
    case 'MY_CUSTOMER':
      component = (<MyCustomerPanel children={item.children} menuId={item.id}/>);
      break;
    case 'DEPARTMENT_OPEN_SEA':
      component = (<div>全力开发中，敬请期待...</div>);
      break;
    case 'COMPANY_OPEN_SEA':
      component = (<div>全力开发中，敬请期待...</div>);
      break;
    default:
      component = (<div>The page you request is not exist.</div>);
  }
  return component;
}
/**
 *  the mycustomer's children component
 * @param customerId
 * @returns {{CUSTOMER_INFO: XML, CUSTOMER_CONTACT: XML, CUSTOMER_CONTRACT: XML}}
 */
export function myCustomerComponentConstant(customerId){
  const myCustomer2Component = {
    CUSTOMER_INFO: (<CustomerInfoCell  customerId={customerId}/>),
    CUSTOMER_CONTACT: (<CustomerContactCell customerId={customerId}/>),
    CUSTOMER_CONTRACT: (<div>coding</div>),
  };
  return myCustomer2Component;
}

export function initNotificationComponent(item){
  var component;
  switch (item.name){
    case 'SYSTEM_NOTIFICATION':
      component = (<CustomerNotificationPanel children={item.children} menuId={item.id}/>);
      break;
    case 'USER_NOTIFICATION':
      component = (<div>全力开发中，敬请期待...</div>);
      break;
    default:
      component = (<div>The page you request is not exist.</div>);
  }
  return component;
}

export function initRoleAndPermissionComponent(item){
  var component;
  switch (item.name){
    case 'ROLES':
      component = (<RoleAndPermissionPanel children={item.children} menuId={item.id}/>);
      break;
    case 'MY_ROLE_AND_PERMISSION':
      component = (<MyRoleAndPermissionPanel children={item.children} menuId={item.id}/>);
      break
    default:
      component = (<div>The page you request is not exist.</div>);
  }
  return component;
}

export function initUserManagementComponent(item){
  return (<CompanyUserPanel menuId={item.id}/>);
}

export function initDepartmentManagementComponent(item){
  return (<DepartmentManagePanel menuId={item.id} />);
}

export function initUserCenterComponent(item){
  return (<UserCenterPanel menuId={item.id}/>);
}
