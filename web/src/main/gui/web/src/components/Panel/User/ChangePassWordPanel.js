/**
 * Created by Administrator on 2018/4/22.
 */
import React from 'react';
import {Form, Input, Button} from 'antd';
const FormItem = Form.Item;

export default class ChangePassWordPanel extends React.Component{
  render(){
    const {onCancelChange, onSubmitChange} = this.props;
    const formItemLayout = {
      labelCol: {
        xs: { span: 8 },
        sm: { span: 8 },
      },
      wrapperCol: {
        xs: { span: 12 },
        sm: { span: 12 },
      },
    };
    const tailFormItemLayout = {
      wrapperCol: {
        xs: {
          span: 8,
          offset: -8,
        },
        sm: {
          span: 8,
          offset: 8,
        },
      },
    };

    return(
      <Form onSubmit={onSubmitChange}>
        <FormItem label="新密码" {...formItemLayout}><Input type="password" size="small" icon="lock"/></FormItem>
        <FormItem label="确认密码" {...formItemLayout} style={{marginTop: '-24px'}}><Input type="password" size="small" icon="lock"/></FormItem>
        <FormItem style={{marginTop: '-20px'}} {...tailFormItemLayout}><Button size="small" onClick={onCancelChange}>取消</Button>
          <Button size="small" type="primary" htmlType="submit" style={{marginLeft:'16px'}}>提交</Button></FormItem>
      </Form>
    );
  }
}
