/**
 * Created by mosesc on 03/21/18.
 */
import React from 'react';
import CommonTab from './CommonTab';

export default class DynamicPanel extends React.Component{

  render(){
    const {children, childrenPanels} = this.props;
    const panels = children ? children.map(item => {
      return item.active ?  {...item, content: childrenPanels[item.name]} : item ;
    }) : children;
    return(
      <div>
        <CommonTab panels={panels}/>
      </div>
    );
  }
}
