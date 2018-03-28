/**
 * Created by mosesc on 03/23/18.
 */
import React from 'react';
import CommonTab from '../../Common/CommonTab';
import {notificationComponentConstant} from '../../../utils/Constant';

export default class NotificationPanel extends React.Component{

  render(){
    const {children} = this.props;
    const panels = children ? children.map(item => {
      return item.active ?  {...item, content: notificationComponentConstant(children)[item.name]} : item;
    }) : children;
    return(
      <div>
        <CommonTab panels={panels}/>
      </div>);
  }
}
