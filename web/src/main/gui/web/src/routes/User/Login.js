import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'dva';
import { Button, Row, Form, Input, Icon, message } from 'antd'
import styles from './Login.less';
import logo from '../../assets/logo.svg';

const FormItem = Form.Item

//export default connect(Form.create()(Login))

@connect(({ loading }) => ({
  loading:loading.effects['login/login'],
}))
@Form.create()
export default class Login extends Component{
  /** when login failed will display the error message **/
  error = (info) => {
    message.error(info);
  }

  handleSubmit = (e) => {
    e.preventDefault();
    this.props.form.validateFieldsAndScroll((errors, values) => {
      if (errors) {
        return;
      }
      this.props.dispatch({ type: 'login/login', payload: values });
      console.log(this.props.status);
      if (this.props.state >= 400) {
        this.error(this.props.data);
      }
      // console.log(props);
      // const {status, data} = state;
      // if (valuesstatus >= 400){
      //   error(data);
      // }
    })
  }

  render(){
    const { getFieldDecorator, getFieldValue } = this.props.form;
    const { loading } = this.props;
    const {status} = this.props;

    return (
      <div className={styles.form}>
        <div className={styles.logo}>
          <img alt="logo" src={logo} />
          <span>{'客户信息管理系统'+status}</span>
        </div>
        <form>
          <FormItem >
            {getFieldDecorator('loginKey', {
              rules: [
                {
                  required: true,
                  message:'请输入用户名',
                },
              ],
            })(<Input onPressEnter={this.handleSubmit} placeholder="用户名" prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}/>)}
          </FormItem>
          <FormItem>
            {getFieldDecorator('password', {
              rules: [
                {
                  required: true,
                  message:'请输入密码',
                },
              ],
            })(<Input type="password" onPressEnter={this.handleSubmit} placeholder="密码"  prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />}/>)}
          </FormItem>
          <Row>
            <Button type="primary" onClick={this.handleSubmit}  loading={loading}>
              登录
            </Button>
            <p>
              <span>1051750377@qq.com/tipcrm</span>
            </p>
          </Row>

        </form>
      </div>
    )
  }
}
