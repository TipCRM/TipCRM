/**
 * Created by mosesc on 03/21/18.
 */
import React from 'react';
import {Tabs} from 'antd';
import styles from './Index.less';
const {TabPane} = Tabs;

export default class CommonTab extends React.Component{
  render(){
    const {panels} = this.props;
    return(
      <Tabs type="card">
        {panels.map(item =>(
          <TabPane tab={<div className={styles['tab-title']}> {item.title}</div>} key={item.id}>
            {item.content}
          </TabPane>))}
      </Tabs>
    );
  }
}
