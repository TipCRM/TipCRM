/**
 * Created by Administrator on 2018/4/22.
 */
import React from 'react';
import {Form, Input, Button, Icon} from 'antd';
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
        <FormItem label="请输入邮箱" {...formItemLayout}><Input size="small" prefix={<Icon type="mail" style={{ color: 'rgba(0,0,0,.25)' }} />}/></FormItem>
        <FormItem label="验证码" {...formItemLayout} style={{marginTop: '-24px'}}><Input  size="small" addonAfter={<a>获取验证码</a>}/></FormItem>
        <FormItem label="新密码" {...formItemLayout} style={{marginTop: '-24px'}}><Input prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />} type="password" size="small" /></FormItem>
        <FormItem label="确认密码" {...formItemLayout} style={{marginTop: '-24px'}}><Input type="password" size="small" prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />}/></FormItem>
        <FormItem style={{marginTop: '-20px'}} {...tailFormItemLayout}><Button size="small" onClick={onCancelChange}>取消</Button>
          <Button size="small" type="primary" htmlType="submit" style={{marginLeft:'16px'}}>提交</Button></FormItem>
      </Form>
    );
  }
}
