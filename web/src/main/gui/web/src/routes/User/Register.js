import React, { Component } from 'react';
import { connect } from 'dva';
import { routerRedux, Link } from 'dva/router';
import { Form, Input, Button, Row, Col, Popover, Progress, Icon } from 'antd';
import styles from './Index.less';
import logo from '../../assets/logo.svg';

const FormItem = Form.Item;

const passwordStatusMap = {
  ok: <div className={styles.success}>强度：强</div>,
  pass: <div className={styles.warning}>强度：中</div>,
  poor: <div className={styles.error}>强度：太短</div>,
};

const passwordProgressMap = {
  ok: 'success',
  pass: 'normal',
  poor: 'exception',
};

@connect(({ register, loading }) => ({
  register,
  submitting: loading.effects['register/submit'],
}))
@Form.create()
export default class Register extends Component {
  state = {
    count: 0,
    confirmDirty: false,
    visible: false,
    help: '',
  };

  componentWillReceiveProps(nextProps) {
    if (nextProps.register.status === 200) {
      this.props.dispatch(routerRedux.push('/user/register-result'));
    }
  }

  componentWillUnmount() {
    clearInterval(this.interval);
  }

  onGetCaptcha = () => {
    let count = 59;
    this.setState({ count });
    this.interval = setInterval(() => {
      count -= 1;
      this.setState({ count });
      if (count === 0) {
        clearInterval(this.interval);
      }
    }, 1000);
  };

  getPasswordStatus = () => {
    const { form } = this.props;
    const value = form.getFieldValue('password');
    if (value && value.length > 9) {
      return 'ok';
    }
    if (value && value.length > 5) {
      return 'pass';
    }
    return 'poor';
  };

  handleSubmit = (e) => {
    e.preventDefault();
    this.props.form.validateFields({ force: true }, (err, values) => {
      if (!err) {
        this.props.dispatch({
          type: 'register/submit',
          payload: {
            ...values,
          },
        });
      }
    });
  };

  handleConfirmBlur = (e) => {
    const { value } = e.target;
    this.setState({ confirmDirty: this.state.confirmDirty || !!value });
  };

  checkConfirm = (rule, value, callback) => {
    const { form } = this.props;
    if (value && value !== form.getFieldValue('password')) {
      callback('两次输入的密码不匹配!');
    } else {
      callback();
    }
  };

  checkPassword = (rule, value, callback) => {
    if (!value) {
      this.setState({
        help: '请输入密码！',
        visible: !!value,
      });
      callback('error');
    } else {
      this.setState({
        help: '',
      });
      if (!this.state.visible) {
        this.setState({
          visible: !!value,
        });
      }
      if (value.length < 6) {
        callback('error');
      } else {
        const { form } = this.props;
        if (value && this.state.confirmDirty) {
          form.validateFields(['confirm'], { force: true });
        }
        callback();
      }
    }
  };

  renderPasswordProgress = () => {
    const { form } = this.props;
    const value = form.getFieldValue('password');
    const passwordStatus = this.getPasswordStatus();
    return value && value.length ? (
      <div className={styles[`progress-${passwordStatus}`]}>
        <Progress
          status={passwordProgressMap[passwordStatus]}
          className={styles.progress}
          strokeWidth={6}
          percent={value.length * 10 > 100 ? 100 : value.length * 10}
          showInfo={false}
        />
      </div>
    ) : null;
  };

  render() {
    const { form, submitting } = this.props;
    const { getFieldDecorator } = form;
    const { count } = this.state;
    return (
      <div className={styles.form} style={{marginTop:'-320px'}}>
        <div className={styles.logo}>
          <img alt="logo" src={logo} />
          <span>{'系统账号注册'}</span>
        </div>
        <Form onSubmit={this.handleSubmit}>
          <FormItem hasFeedback>
            {getFieldDecorator('username', {
              rules: [
                {
                  required: true,
                  message:'请输入用户名'
                },
              ],
            })(<Input onPressEnter={this.handleSubmit} placeholder="用户名" prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}/>)}
          </FormItem>
          <FormItem hasFeedback>
            {getFieldDecorator('email', {
              rules: [
                {
                  required: true,
                  message: '请输入邮箱地址！',
                },
                {
                  type: 'email',
                  message: '邮箱地址格式错误！',
                },
              ],
            })(<Input placeholder="邮箱" prefix={<Icon type="mail" style={{ color: 'rgba(0,0,0,.25)' }}/>} />)}
          </FormItem>
          <FormItem help={this.state.help} hasFeedback>
            <Popover
              content={
                <div style={{ padding: '4px 0' }}>
                  {passwordStatusMap[this.getPasswordStatus()]}
                  {this.renderPasswordProgress()}
                  <div style={{ marginTop: 10 }}>
                    请至少输入 6 个字符。请不要使用容易被猜到的密码。
                  </div>
                </div>
              }
              overlayStyle={{ width: 240 }}
              placement="right"
              visible={this.state.visible}
            >
              {getFieldDecorator('password', {
                rules: [
                  {
                    validator: this.checkPassword,
                  },
                ],
              })(
                <Input
                  type="password"
                  placeholder="至少6位密码，区分大小写"
                  prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }}/>}
                />
              )}
            </Popover>
          </FormItem>
          <FormItem hasFeedback>
            {getFieldDecorator('confirm', {
              rules: [
                {
                  required: true,
                  message: '请确认密码！',
                },
                {
                  validator: this.checkConfirm,
                },
              ],
            })(<Input
              type="password"
              placeholder="确认密码"
              prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }}/>}
            />)}
          </FormItem>
          <FormItem hasFeedback>
              {getFieldDecorator('mobile', {
                rules: [
                  {
                    required: true,
                    message: '请输入手机号！',
                  },
                  {
                    pattern: /^1\d{10}$/,
                    message: '手机号格式错误！',
                  },
                ],
              })(
                <Input
                  placeholder="11位手机号"
                  prefix={<Icon type="mobile" style={{ color: 'rgba(0,0,0,.25)' }}/>}
                />
              )}
          </FormItem>
          <FormItem>
            <Row gutter={8}>
              <Col span={16}>
                {getFieldDecorator('captcha', {
                  rules: [
                    {
                      required: true,
                      message: '请输入验证码！',
                    },
                  ],
                })(<Input placeholder="验证码" />)}
              </Col>
              <Col span={8}>
                <Button
                  disabled={count}
                  className={styles.getCaptcha}
                  onClick={this.onGetCaptcha}
                >
                  {count ? `${count} s` : '验证码'}
                </Button>
              </Col>
            </Row>
          </FormItem>
          <FormItem>
            <Button
              loading={submitting}
              className={styles.submit}
              type="primary"
              htmlType="submit">
              注册
            </Button>
            <Link className={styles.login} to="/user/login">
              使用已有账户登录
            </Link>
          </FormItem>
        </Form>
      </div>
    );
  }
}
