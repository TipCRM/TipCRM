/**
 * Created by mosesc on 03/21/18.
 */
import React from 'react';
import {Tabs} from 'antd';
const {TabPane} = Tabs;

export default class CommonTab extends React.Component{
  render(){
    const {panels} = this.props;
    return(
      <Tabs type="card">
        {panels.map(item =>(
          <TabPane tab={item.title} key={item.key}>
            {item.content}
          </TabPane>))}
      </Tabs>
    );
  }
}
