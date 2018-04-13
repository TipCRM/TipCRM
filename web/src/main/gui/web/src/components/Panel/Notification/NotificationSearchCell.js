/**
 * Created by mosesc on 03/23/18.
 */
import React from 'react';
import {Button} from 'antd';
import CommonTagGroup from '../../Common/CommonTagGroup';

export default class NotificationSearchCell extends React.Component{
  render(){
    const {tags} = this.props;
    return(<div style={{marginBottom:'8px'}}>
      <CommonTagGroup tags={tags}/>
    </div>);
  }
}
