/**
 * Created by mosesc on 03/21/18.
 */
import React from 'react';
import CommonTab from './CommonTab';

export default class DynamicPanel extends React.Component{

  render(){
    const {children, initChildrenPanel, item, notTabs} = this.props;
    // if has no children, then return a panel not tabs
    if (notTabs){
      return (<div>{initChildrenPanel(item)}</div>);
    }
    const panels = children ? children.map(item => {
      return item.active ?  {...item, content: initChildrenPanel(item)} : item ;
    }) : children;
    return(
      <div>
        <CommonTab panels={panels}/>
      </div>
    );
  }
}
