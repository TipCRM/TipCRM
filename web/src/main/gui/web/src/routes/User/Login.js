import React from 'react';
import {Link} from 'dva/router';
import { connect } from 'dva';
import { Button, Row, Form, Input, Icon } from 'antd';
import styles from './Index.less';
import logo from '../../assets/logo.svg';

const FormItem = Form.Item;

@connect(({loading}) =>
  ({loading: loading.effects['user/login'],})
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
        { type: 'user/login',
          payload: values
        });
    })
  }

  render(){
    const {getFieldDecorator} = this.props.form;
    const {loading} = this.props;
    //console.log(this.props.data);
    return (
      <div className={styles.form}>
        <div className={styles.logo}>
          <img alt="logo" src={logo} />
          <span>{'客户信息管理系统'}</span>
        </div>
        <form>
          <FormItem style={{marginBottom:'15px'}}>
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
          <FormItem>
            <Button type="primary"
                    onClick={this.handleSubmit}
                    loading={loading}
            >
              登录
            </Button>
          </FormItem>
          <Row>
            <span>1051750377@qq.com/tipcrm</span>
          </Row>
        </form>
      </div>
    );
  }
}
