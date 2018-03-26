/**
 * Created by mosesc on 03/21/18.
 */
import React from 'react';
import {Modal} from 'antd';
import CommonTab from '../../Common/CommonTab';

export default class CustomerDetailPanel extends React.Component{
  render(){
    const {visible, title, panels, onCancel} = this.props;
    return(
      <Modal visible={visible} footer={null} title={title} onCancel={onCancel} width="70%" destroyOnClose={true}>
        <CommonTab panels={panels}/>
      </Modal>
    );
  }
}
