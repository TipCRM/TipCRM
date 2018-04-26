/**
 * Created by Administrator on 2018/4/22.
 */
import React from 'react';
import {Form, Input, Button, Icon, Row, Col, Popover, Progress} from 'antd';
import styles from './Index.less';
const FormItem = Form.Item;

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

@Form.create()
export default class ChangePassWordPanel extends React.Component{
  state = {
    confirmDirty: false,
    visible: false,
    help: '',
  };

  // show the password level
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

  // check repeat password
  checkConfirm = (rule, value, callback) => {
    const { form } = this.props;
    if (value && value !== form.getFieldValue('password')) {
      callback('两次输入的密码不匹配!');
    } else {
      callback();
    }
  };

  // check password
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
        callback();
      } else {
        const { form } = this.props;
        if (value && this.state.confirmDirty) {
          form.validateFields(['confirm'], { force: true });
        }
        callback();
      }
    }
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

  render(){
    const {onCancelChange, onSubmitChange, onGetValidationCode, count, onInputEmailChange, onPasswordChange,submittingChange,
      onRepeatPasswordChange, inputEmail, newPassword, repeatPassword, validationCode, form, gettingValidationCode, onValidationCodeChange} = this.props;
    const {getFieldDecorator} = form;

    return(
      <Form onSubmit={onSubmitChange}>
        <FormItem label="请输入邮箱" {...formItemLayout}><Row gutter={8}>{
          getFieldDecorator('email', {
            rules: [
              {
                required: true,
                message: '请输入邮箱！',
              },
              {
                type: 'email',
                message: '邮箱地址格式错误！',
              }
            ],
          })(<Input prefix={<Icon type="mail" style={{ color: 'rgba(0,0,0,.25)' }}/>} value={inputEmail} onChange={onInputEmailChange} placeholder="输入邮箱"/>)
        }
        </Row></FormItem>
        <FormItem label="验证码" {...formItemLayout} style={{marginTop: '-20px'}}>
          <Row gutter={8}>
          <Col span={14}>
            {getFieldDecorator('captcha', {
              rules: [
                {
                  required: true,
                  message: '请输入验证码！',
                },
              ],
            })(<Input placeholder="输入验证码" value={validationCode} onChange = {onValidationCodeChange}/>)}
          </Col>
          <Col span={10}>
            <Button
              disabled={count}
              loading={gettingValidationCode}
              onClick={onGetValidationCode}
              style={{width:'100%'}}
            >
              {count ? `${count} s` : '获取验证码'}
            </Button>
          </Col>
        </Row></FormItem>
        <FormItem label="新密码" {...formItemLayout} style={{marginTop: '-20px'}} >
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
                required: true,
                message: '请输入密码！',
              },
              {
                validator: this.checkPassword,
              }
            ],
          })(<Input value={newPassword} onChange={onPasswordChange} prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }}/>} type="password" placeholder="输入新密码"/>)}
        </Popover>
        </FormItem>
        <FormItem label="确认密码" {...formItemLayout} style={{marginTop: '-20px'}}>
          {getFieldDecorator('repeatPassword', {
            rules: [
              {
                required: true,
                message: '请重复输入密码！',
              }, {
                validator: this.checkConfirm
              }
            ],
          })(<Input value={repeatPassword} onChange={onRepeatPasswordChange} type="password" prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }}/>} placeholder="请重复输入密码"/>)}
        </FormItem>
        <FormItem style={{marginTop: '-20px'}} {...tailFormItemLayout}>
          <Button  onClick={onCancelChange}>取消</Button>
          <Button  type="primary" htmlType="submit" style={{marginLeft:'16px'}} loading={submittingChange}>提交</Button>
        </FormItem>
      </Form>
    );
  }
}
