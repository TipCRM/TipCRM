/**
 * Created by Administrator on 2018/4/22.
 */
import React from 'react';
import {Form, Input, Button, Icon, Row, Col} from 'antd';
const FormItem = Form.Item;

@Form.create()
export default class ChangePassWordPanel extends React.Component{
  render(){
    const {onCancelChange, onSubmitChange, onGetValidationCode, count,
      inputEmail, newPassword, repeatPassword, validationCode, form} = this.props;
    const {getFieldDecorator} = form;
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
        <FormItem label="请输入邮箱" {...formItemLayout}><Row gutter={8}><Input prefix={<Icon type="mail" style={{ color: 'rgba(0,0,0,.25)' }} value={inputEmail}/>}/></Row></FormItem>
        <FormItem label="验证码" {...formItemLayout} style={{marginTop: '-24px'}}>
          <Row gutter={8}>
          <Col span={16}>
            {getFieldDecorator('captcha', {
              rules: [
                {
                  required: true,
                  message: '请输入验证码！',
                },
              ],
            })(<Input placeholder="验证码" value={validationCode}/>)}
          </Col>
          <Col span={8}>
            <Button
              disabled={count}
              onClick={onGetValidationCode}
            >
              {count ? `${count} s` : '获取验证码'}
            </Button>
          </Col>
        </Row></FormItem>
        <FormItem label="新密码" {...formItemLayout} style={{marginTop: '-24px'}}><Input value={newPassword} prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />} type="password"/></FormItem>
        <FormItem label="确认密码" {...formItemLayout} style={{marginTop: '-24px'}}><Input value={repeatPassword} type="password" prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />}/></FormItem>
        <FormItem style={{marginTop: '-20px'}} {...tailFormItemLayout}><Button  onClick={onCancelChange}>取消</Button>
          <Button  type="primary" htmlType="submit" style={{marginLeft:'16px'}}>提交</Button></FormItem>
      </Form>
    );
  }
}
