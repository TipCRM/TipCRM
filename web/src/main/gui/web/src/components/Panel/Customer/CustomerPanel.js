/**
 * Created by mosesc on 03/21/18.
 */
import React from 'react';
import CommonTab from '../../Common/CommonTab';
import {customerComponentConstant} from '../../../utils/Constant';

export default class CustomerPanel extends React.Component{

  render(){
    const {children} = this.props;
    const panels = children ? children.map(item => {
      return item.active ?  {...item, content: customerComponentConstant(item.children)[item.name]} : item ;
    }) : children;
    return(
      <div>
          <CommonTab panels={panels}/>
      </div>
    );
  }
}
