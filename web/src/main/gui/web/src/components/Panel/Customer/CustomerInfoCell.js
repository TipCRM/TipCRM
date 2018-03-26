/**
 * Created by mosesc on 03/22/18.
 */
import React from 'react';
import {Collapse, Row} from 'antd';
const Panel = Collapse.Panel;

export default class CustomerInfoCell extends React.Component{
  render(){
    const {customerId} = this.props;
    const customer = {name:'大联盟科技有公司',contact:'李晓明',contactPhone:'187 8523 6957',};
    return(
      <Collapse defaultActiveKey={['0','1']}>
        <Panel showArrow={false} header="客户基本信息">
          <Row>客户ID：{customerId}</Row>
          <Row>客户名称：{customer.name}</Row>
          <Row>联系人：{customer.contact}</Row>
          <Row>联系电话：{customer.contactPhone}</Row>
        </Panel>
        <Panel showArrow={false} header="客户其他信息">
          <Row>客户重要程度：******</Row>
          <Row>客户地址：上海市华东区</Row>
          <Row>客户QQ：123215452</Row>
        </Panel>
      </Collapse>
    );
  }
}
