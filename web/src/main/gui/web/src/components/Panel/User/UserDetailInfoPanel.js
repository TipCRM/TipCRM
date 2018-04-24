/**
 * Created by mosesc on 04/11/18.
 */
import React from 'react';
import CommonSpin from '../../Common/CommonSpin';
import {connect} from 'dva';
import {Input, Button, Icon, Col, Upload, Form, Modal} from 'antd';
import ChangePassWordPanel from './ChangePassWordPanel';
import styles from './Index.less';
const FormItem = Form.Item;
const {TextArea} = Input;

@connect(({loading, user})=>({
  userLoading: loading.models.user,
  selectUserInfo: user.selectUserInfo
}))
export default class UserDetailInfoPanel extends React.Component{
  state={
    editing: this.props.createNew,
    createNew: this.props.createNew,
    showChangePassword: false,
  }
  componentDidMount(){
    const {dispatch, selectUser} = this.props;
    if (selectUser.id){
      dispatch({
        type: 'user/getUserDetailInfo',
        payload: {userId: selectUser.id},
      });
    }
  }

  handleSaveUser(selectUserInfo){
    const {dispatch} = this.props;
    if (selectUserInfo.id){
      dispatch({
        type: 'user/updateUser',
        payload: {}
      });
    } else{
      dispatch({
        type: 'user/createNewUser',
        payload: {}
      });
    }
    this.setState({
      editing: false,
      createNew: false,
    });
  }
  handleEditUser(){
    this.setState({
      editing: true,
    });
  }
  handleCancelSave(){
    this.setState({
      editing: false,
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
  handleSubmitChangePassword(){
    console.log("submit change password");
  }

  render(){
    const {selectUserInfo, userLoading, enableEdit} = this.props;
    const {editing, createNew, showChangePassword} =  this.state;
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
    return(<CommonSpin spinning={userLoading}>
      <div>
            <div style={{float: 'left', width: '30%', marginTop: '15%'}}>
              <div>
                <Upload
                  name="avatar"
                  listType="picture-card"
                  disabled={!editing}
                  action="/avatar"
                >
                  {selectUserInfo.avatar ? <img src={"http://www.potafish.com/avatar/"+selectUserInfo.avatar} style={{height:'100%', width:'100%'}}/> : ""}
                </Upload>
              </div>
              <div  style={{marginBottom: '8px'}}>
                <a onClick={this.handleOpenChangePassword.bind(this)}><Icon type="lock"/>修改密码</a>
                <Modal footer="" visible={showChangePassword} title="修改密码" width="30%"
                       onCancel={this.handleCloseChangePassword.bind(this)} destroyOnClose>
                  <ChangePassWordPanel onSubmitChange={this.handleSubmitChangePassword.bind(this)} onCancelChange={this.handleCloseChangePassword.bind(this)}/>
                </Modal>
              </div>
              <div>
                <b>座右铭：</b>
                <TextArea row="4" value={selectUserInfo.motto} style={{marginTop: '4px'}} disabled={!editing}/>
              </div>
            </div>
            <div style={{float: 'left', width:'70%'}}>
              <Form>
                <FormItem label="员工编号" {...formItemLayout} style={{marginTop:'-24px'}}>{selectUserInfo.workNo}</FormItem>
                <FormItem label="用户名" {...formItemLayout} style={{marginTop:'-24px'}}><Input value={selectUserInfo.name} size="small" disabled={!editing}/></FormItem>
                <FormItem label="所在部门" {...formItemLayout} style={{marginTop:'-24px'}}>{selectUserInfo.department}</FormItem>
                <FormItem label="员工等级" {...formItemLayout} style={{marginTop:'-24px'}}>{selectUserInfo.level}</FormItem>
                <FormItem label="邮箱" {...formItemLayout} style={{marginTop:'-24px'}}><Input value={selectUserInfo.email} disabled={!editing} size="small"/></FormItem>
                <FormItem label="联系电话" {...formItemLayout} style={{marginTop:'-24px'}}><Input value={selectUserInfo.phoneNo} size="small" disabled={!editing}/></FormItem>
                <FormItem label="身份证" {...formItemLayout} style={{marginTop:'-24px'}}><Input value={selectUserInfo.idCard} size="small" disabled={!editing}/></FormItem>
                <FormItem label="入职时间" {...formItemLayout} style={{marginTop:'-24px'}}>{selectUserInfo.hireTime}</FormItem>
                <FormItem label="经办人" {...formItemLayout} style={{marginTop:'-24px'}}>{selectUserInfo.hirer}</FormItem>
                <FormItem label="状态" {...formItemLayout} style={{marginTop:'-24px'}}>{selectUserInfo.status}</FormItem>
              </Form>
            </div>
      </div>
      <div style={{textAlign: 'center',clear: 'both'}}>
        {
          editing ? <div>{
            (enableEdit && !createNew) ? <Button onClick={this.handleCancelSave.bind(this)} size="small">取消</Button>: ''
          }<Button type="primary" style={{marginLeft: '8px', marginRight:'8px'}} icon="save" size="small" onClick={this.handleSaveUser.bind(this, selectUserInfo)}>保存</Button></div> :
            (enableEdit ? <Button icon="edit" type="primary" size="small" onClick={this.handleEditUser.bind(this)}>修改</Button> : '')
        }
      </div>
    </CommonSpin>);
  }
}
