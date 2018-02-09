import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'dva';
import { Button, Row, Form, Input, Icon, message } from 'antd'
import styles from './Login.less';
import logo from '../../assets/logo.svg';

const FormItem = Form.Item;

@connect(({loading}) =>
  ({loading: loading.effects['login/login'],})
)
@Form.create()
export default class Login extends React.PureComponent{

  /**
   * user login action
   * @param e
     */
  handleSubmit = (e) => {
    e.preventDefault();
    this.props.form.validateFieldsAndScroll((errors, values) => {
      if (errors) {
        return;
      }
      this.props.dispatch(
        { type: 'login/login',
          payload: values
        });
    })
  }

  render(){
    const {getFieldDecorator} = this.props.form;
    //console.log(this.props.data);
    return (
      <div className={styles.form}>
        <div className={styles.logo}>
          <img alt="logo" src={logo} />
          <span>{'信息管理系统'}</span>
        </div>
        <form>
          <FormItem>
            {getFieldDecorator('loginKey', {
              rules: [
                {
                  required: true,
                  message:'请输入用户名'
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
            <Button type="primary" onClick={this.handleSubmit} loading={this.loading}>
              登录
            </Button>
            <p>
              <span>1051750377@qq.com/tipcrm</span>
            </p>
          </Row>
        </form>
      </div>
    );
  }
}
