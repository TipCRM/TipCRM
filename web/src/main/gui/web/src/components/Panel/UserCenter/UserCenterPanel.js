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

@connect(({loading, user, util}) =>({
  changingMyInfo: loading.effects['user/changeMyInfo'],
  gettingValidationCode:loading.effects['util/getValidationCode'],
  submittingChange: loading.effects['user/changePassword'],
  currentUser: user.currentUser,
  successGetValidationCode: util.successGetValidationCode,
  editingMyInfo: user.editingMyInfo
}))
export default class UserCenterPanel extends React.Component{
  state={
    loading: false,
    showChangePassword: false,
    inputEmail: null,
    newPassword: null,
    repeatPassword: null,
    validationCode: null,
    count: 0,
    changeData: null,
  }

  componentWillMount(){
    const {currentUser} = this.props;
    this.setState({
      changeData: {
        avatar: currentUser.avatar,
        avatarType: 0,
        birthday: currentUser.birthday,
        email: currentUser.email,
        id: currentUser.id,
        idCard: currentUser.idCard,
        motto: currentUser.motto,
        phoneNo: currentUser.phoneNo,
        userName: currentUser.name,
      }
    });
  }
  handleCancelSave(){
    const {dispatch} = this.props;
    dispatch({
      type: 'user/saveEditStatus',
      payload: false,
    })
  }
  handleSaveUser(){
    const {dispatch} = this.props;
    const {changeData} = this.state;
    dispatch({
      type: 'user/changeMyInfo',
      payload: changeData
    });
  }
  handleEditUserInfo(){
    const {dispatch} = this.props;
    dispatch({
      type: 'user/saveEditStatus',
      payload: true,
    })
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
    const {dispatch, successGetValidationCode} = this.props;
    dispatch({
      type: 'util/getValidationCode',
      payload: {type: 'CHANGE_PASSWORD', email: inputEmail}
    });
    let count = '';
    this.setState({ count });
    count = 60;
    this.interval = setInterval(() => {
      if (!successGetValidationCode){
        count = 0;
      } else {
        count -= 1;
      }
      this.setState({ count });
      if (count === 0) {
        clearInterval(this.interval);
        dispatch({
          type: 'util/saveGetValidationCodeStatus',
          payload: true,
        });
      }
    }, 1000);
  }
  handleSubmitChangePassword(e){
    e.preventDefault();
    const {dispatch} = this.props;
    const {newPassword, repeatPassword, validationCode, inputEmail} = this.state;

    if (newPassword && (newPassword.length >= 6) && (newPassword === repeatPassword)
      && validationCode && inputEmail){
      let request = {
        newPassword: newPassword,
        validationCode: validationCode
      };
      dispatch({
        type: 'user/changePassword',
        payload: request,
      });
      return;
    }
    message.error("请补全信息,且密码长度不小于6位！");
  }
  handleInputEmailChange(e){
    this.setState({
      inputEmail: e.target.value,
    });
  }
  handlePasswordChange(e){
    this.setState({
      newPassword: e.target.value,
    });
  }
  handleRepeatPasswordChange(e){
    this.setState({
      repeatPassword: e.target.value,
    });
  }
  handleValidationCodeChange(e){
    this.setState({
      validationCode: e.target.value,
    });
  }

  // common handle the edit value
  handleCommonValueChange(field, e){
    console.log("e", e);
    console.log("filed", field);
    let {changeData} = this.state;
    changeData[field] = e.target.value;
    this.setState({
      changeData: changeData,
    })
  }

  render(){
    const {currentUser, gettingValidationCode, editingMyInfo, changingMyInfo} = this.props;
    const { showChangePassword, validationCode, inputEmail,
      newPassword, repeatPassword, count, submittingChange, changeData} =  this.state;
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
      <div style={{height:'150px', width:'150px'}}>
        <Icon type={this.state.loading ? 'loading' : 'plus'} />
        <div className="ant-upload-text">上传</div>
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
                disabled={!editingMyInfo}
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
                                     count = {count} gettingValidationCode={gettingValidationCode}
                                     onPasswordChange = {this.handlePasswordChange.bind(this)}
                                     onRepeatPasswordChange = {this.handleRepeatPasswordChange.bind(this)}
                                     newPassword = {newPassword} repeatPassword={repeatPassword}
                                     onGetValidationCode = {this.handleGetValidationCode.bind(this, inputEmail)}
                                     onInputEmailChange = {this.handleInputEmailChange.bind(this)}
                                     onSubmitChange={this.handleSubmitChangePassword.bind(this)}
                                     submittingChange = {submittingChange} onValidationCodeChange={this.handleValidationCodeChange.bind(this)}
                                     onCancelChange={this.handleCloseChangePassword.bind(this)}/>
              </Modal>
            </div>

            <div>
              <b>座右铭：</b>
              <TextArea row="4" value={editingMyInfo ? changeData.motto : currentUser.motto} style={{marginTop: '4px'}}
                        onChange={this.handleCommonValueChange.bind(this, 'motto')} disabled={!editingMyInfo}/>
            </div>
          </div>
          <div style={{float: 'left', width:'70%'}}>
            <Form style={{marginTop: '16px'}}>
              <FormItem label="员工编号" {...formItemLayout} style={{marginTop:'-24px'}}>{currentUser.workNo}</FormItem>
              <FormItem label="用户名" {...formItemLayout} style={{marginTop:'-20px'}}>
                {editingMyInfo ? <Input value={changeData.userName} onChange={this.handleCommonValueChange.bind(this, 'userName')} width="60%"/> : currentUser.name}
              </FormItem>
              <FormItem label="邮箱" {...formItemLayout} style={{marginTop:'-20px'}}>
                {editingMyInfo ? <Input value={changeData.email} onChange={this.handleCommonValueChange.bind(this, 'email')} width="60%"/> : currentUser.email}
              </FormItem>
              <FormItem label="联系电话" {...formItemLayout} style={{marginTop:'-20px'}}>
                {editingMyInfo ? <Input value={changeData.phoneNo} onChange={this.handleCommonValueChange.bind(this, 'phoneNo')} width="60%"/> : currentUser.phoneNo}</FormItem>
              <FormItem label="身份证" {...formItemLayout} style={{marginTop:'-20px'}}>
                {editingMyInfo ? <Input value={changeData.idCard} onChange={this.handleCommonValueChange.bind(this, 'idCard')} width="60%"/> : currentUser.idCard}
              </FormItem>
              <FormItem label="所在部门" {...formItemLayout} style={{marginTop:'-24px'}}>{currentUser.department}</FormItem>
              <FormItem label="员工等级" {...formItemLayout} style={{marginTop:'-24px'}}>{currentUser.level}</FormItem>
              <FormItem label="提成比例" {...formItemLayout} style={{marginTop:'-24px'}}>{currentUser.paymentPercentage}%</FormItem>
              <FormItem label="入职时间" {...formItemLayout} style={{marginTop:'-24px'}}>{currentUser.hireTime}</FormItem>
              <FormItem label="经办人" {...formItemLayout} style={{marginTop:'-24px'}}>{currentUser.hirer}</FormItem>
            </Form>
          </div>
        </div>
        <div style={{textAlign: 'center',clear: 'both'}}>
          {
            editingMyInfo ?
              <div>
                <Button onClick={this.handleCancelSave.bind(this)}>取消</Button>
                <Button type="primary" style={{marginLeft: '8px', marginRight:'16px'}} icon="save"
                        onClick={this.handleSaveUser.bind(this)} loading={changingMyInfo}>保存</Button>
              </div> :
              <Button type="primary" style={{marginLeft: '8px', marginRight:'16px'}} icon="edit"
                      onClick={this.handleEditUserInfo.bind(this)} loading={changingMyInfo}>修改</Button>
          }
        </div>
      </div>
    </CommonSpin>);
  }
}
