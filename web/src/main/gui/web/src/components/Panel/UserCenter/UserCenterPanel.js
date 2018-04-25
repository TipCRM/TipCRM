/**
 * Created by mosesc on 04/20/18.
 */
import React from 'react';
import {connect} from 'dva';
import {Upload, Icon, Modal, Form, Input, Button, message} from 'antd';
import ChangePassWordPanel from './ChangePassWordPanel';
import CommonSpin from '../../Common/CommonSpin';
const FormItem = Form.Item;
const {TextArea} = Input;

@connect(({loading, user}) =>({
  currentUserloading : loading.effects['user/getCurrentUser'],
  currentUser: user.currentUser,
}))

export default class UserCenterPanel extends React.Component{
  state={
    loading: false,
    editing: false,
    showChangePassword: false,
    inputEmail: null,
    newPassword: null,
    repeatPassword: null,
    validationCode: null,
    count: 0,
  }

  componentDidMount(){
  }
  handleCancelSave(){
    this.setState({
      editing: false,
    });
  }
  handleSaveUser(){
    this.setState({
      editing: false,
    });
  }
  handleEditUserInfo(){
    this.setState({
      editing: true,
    });
  }

  handleOpenChangePassword(){
    this.setState({
      showChangePassword: true
    });
  }
  handleCloseChangePassword(){
    this.setState({
      showChangePassword: false
    });
  }
  handleGetValidationCode(inputEmail){
    const reg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
    if (inputEmail == null || !reg.test(inputEmail)){
      message.error("邮箱地址不合法！");
      return;
    }
    let count = 59;
    this.setState({ count });
    this.interval = setInterval(() => {
      count -= 1;
      this.setState({ count });
      if (count === 0) {
        clearInterval(this.interval);
      }
    }, 1000);
  }
  handleSubmitChangePassword(){
    const {dispatch} = this.props;
    let request = {};
    dispatch({
      type: 'user/changePassword',
      payload: request,
    });
  }

  render(){
    const {currentUser} = this.props;
    const { showChangePassword, editing, validationCode, inputEmail, newPassword, repeatPassword, count} =  this.state;
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
    const uploadButton = (
      <div>
        <Icon type={this.state.loading ? 'loading' : 'plus'} />
        <div className="ant-upload-text">Upload</div>
      </div>
    );
    return(<CommonSpin spinning={false}>
      <div style={{marginLeft: '15%', marginRight: '15%'}}>
        <div>
          <div style={{float: 'left', width: '30%', marginTop: '10%'}}>
            <div>
              <Upload
                name="avatar"
                listType="picture-card"
                disabled={!editing}
                action="/avatar"
              >
                {currentUser.avatar ? <img src={"http://www.potafish.com/avatar/"+currentUser.avatar} style={{height:'150px', width:'150px'}}/> : uploadButton}
              </Upload>
            </div>
            <div  style={{marginBottom: '8px'}}>
              <a onClick={this.handleOpenChangePassword.bind(this)}><Icon type="lock"/>修改密码</a>
              <Modal footer="" visible={showChangePassword} title="修改密码" width="30%"
                     onCancel={this.handleCloseChangePassword.bind(this)} destroyOnClose>
                <ChangePassWordPanel inputEmail ={inputEmail} validationCode={validationCode}
                                     count = {count}
                                     newPassword = {newPassword} repeatPassword={repeatPassword}
                                     onGetValidationCode = {this.handleGetValidationCode.bind(this, inputEmail)}
                                     onSubmitChange={this.handleSubmitChangePassword.bind(this)}
                                     onCancelChange={this.handleCloseChangePassword.bind(this)}/>
              </Modal>
            </div>

            <div>
              <b>座右铭：</b>
              <TextArea row="4" value={currentUser.motto} style={{marginTop: '4px'}} disabled={!editing}/>
            </div>
          </div>
          <div style={{float: 'left', width:'70%'}}>
            <Form style={{marginTop: '16px'}}>
              <FormItem label="员工编号" {...formItemLayout} style={{marginTop:'-24px'}}>{currentUser.workNo}</FormItem>
              <FormItem label="用户名" {...formItemLayout} style={{marginTop:'-24px'}}>
                {editing ? <Input value={currentUser.name} size="small"/> : currentUser.name}
              </FormItem>
              <FormItem label="邮箱" {...formItemLayout} style={{marginTop:'-24px'}}>
                {editing ? <Input value={currentUser.email} size="small"/> : currentUser.email}
              </FormItem>
              <FormItem label="联系电话" {...formItemLayout} style={{marginTop:'-24px'}}>
                {editing ? <Input value={currentUser.phoneNo} size="small"/> : currentUser.phoneNo}</FormItem>
              <FormItem label="身份证" {...formItemLayout} style={{marginTop:'-24px'}}>
                {editing ? <Input value={currentUser.idCard} size="small"/> : currentUser.idCard}
              </FormItem>
              <FormItem label="所在部门" {...formItemLayout} style={{marginTop:'-24px'}}>{currentUser.department}</FormItem>
              <FormItem label="员工等级" {...formItemLayout} style={{marginTop:'-24px'}}>{currentUser.level}</FormItem>
              <FormItem label="提成比例" {...formItemLayout} style={{marginTop:'-24px'}}>currentUser.hireTime}</FormItem>
              <FormItem label="入职时间" {...formItemLayout} style={{marginTop:'-24px'}}>{currentUser.hireTime}</FormItem>
              <FormItem label="经办人" {...formItemLayout} style={{marginTop:'-24px'}}>{currentUser.hirer}</FormItem>
              <FormItem label="状态" {...formItemLayout} style={{marginTop:'-24px'}}>{currentUser.status}</FormItem>
            </Form>
          </div>
        </div>
        <div style={{textAlign: 'center',clear: 'both'}}>
          {
            editing ?
              <div>
                <Button onClick={this.handleCancelSave.bind(this)} size="small">取消</Button>
                <Button type="primary" style={{marginLeft: '8px', marginRight:'8px'}} icon="save" size="small" onClick={this.handleSaveUser.bind(this)}>保存</Button>
              </div> :
              <Button type="primary" style={{marginLeft: '8px', marginRight:'8px'}} icon="edit" size="small" onClick={this.handleEditUserInfo.bind(this)}>修改</Button>
          }
        </div>
      </div>
    </CommonSpin>);
  }
}
