/**
 * Created by mosesc on 03/28/18.
 */
import CustomerPanel from '../components/Panel/Customer/CustomerPanel';
import MyCustomerPanel from '../components/Panel/Customer/MyCustomerPanel';
import CustomerInfoCell from '../components/Panel/Customer/CustomerInfoCell';
import CustomerContactCell from '../components/Panel/Customer/CustomerContactCell';

import NotificationPanel from '../components/Panel/Notification/NotificationPanel';
import CustomerNotificationPanel from '../components/Panel/Notification/CustomerNotificationPanel';
/**
 * the menu's component
 * @returns {*[]}
 */
export function menuComponentConstant(children){
  const menu2Component = {
    CUSTOMER: (<CustomerPanel children={children}/>),
    NOTIFICATION: (<NotificationPanel children={children}/>),};
  return menu2Component;
}

export function customerComponentConstant(children){
  const customer2Component = {
    MY_CUSTOMER: (<MyCustomerPanel children={children}/>),
  };
  return customer2Component;
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

export function notificationComponentConstant(children){
  const notification2Component = {
    CUSTOMER_NOTIFICATION : (<CustomerNotificationPanel children={children} />),
  };
  return notification2Component;
}
