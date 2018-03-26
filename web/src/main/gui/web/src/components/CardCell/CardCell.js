/**
 * Created by mosesc on 03/21/18.
 */
import React from 'react';
import {Card, Modal} from 'antd';

export default class CardCell extends React.Component{

  state={
    showModal: false,
  };

  handlerCardClick(){
    this.setState({
      showModal: true,
    });
  }
  handlerModalCancel(){
    this.setState({
      showModal: false,
    });
  }

  render(){
    const {menu, content} = this.props;
    const {showModal} = this.state;
    return(
      <div>
        <Card onClick={this.handlerCardClick.bind(this)} style={{cursor:'pointer'}}>{menu}</Card>
        <Modal width="80%" title={menu} visible={showModal} onCancel={this.handlerModalCancel.bind(this)} footer={null} destroyOnClose={true}>
          {content}
        </Modal>
      </div>);
  }
}
