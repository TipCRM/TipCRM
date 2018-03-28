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
      return item.content ? item : {...item, content: customerComponentConstant(item.children)[item.name]};
    }) : children;
    return(
      <div>
          <CommonTab panels={panels}/>
      </div>
    );
  }
}
