/**
 * Created by mosesc on 04/11/18.
 */
import React from 'react';
import CommonSpin from '../../Common/CommonSpin';
import {connect} from 'dva';
import {Input, Button} from 'antd';
import styles from './Index.less';

@connect(({loading, user})=>({
  userLoading: loading.models.user,
  selectUserInfo: user.selectUserInfo
}))
export default class UserDetailInfoPanel extends React.Component{
  state={
    editing: this.props.createNew,
    createNew: this.props.createNew,
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

  render(){
    const {selectUserInfo, userLoading, enableEdit} = this.props;
    const {editing, createNew} =  this.state;
    const dataSource = [
      {title: '用户名', value: selectUserInfo.name},
      {title: '联系电话', value: selectUserInfo.phone},
      {title: '邮箱', value: selectUserInfo.email},
      {title: '员工状态', value: selectUserInfo.status},
      {title: '提成比例', value: selectUserInfo.status},
      {title: '所在部门', value: selectUserInfo.status},
      {title: '职位', value: selectUserInfo.status},
      {title: '客户数量限制', value: selectUserInfo.status},];
    return(<CommonSpin spinning={userLoading}>
      <div>
        {
          dataSource.map(item =>
            <Input addonBefore={item.title}
                   value={item.value} disabled={!editing}
                   style={{marginBottom: '8px', marginLeft:'8px', marginRight: '8px', width:'31%'}}/>)
        }
      </div>
      <div style={{textAlign: 'center'}}>
        {
          editing ? <div>{
            (enableEdit && !createNew) ? <Button onClick={this.handleCancelSave.bind(this)}>取消</Button>: ''
          }<Button type="primary" style={{marginLeft: '8px', marginRight:'8px'}} icon="save" onClick={this.handleSaveUser.bind(this, selectUserInfo)}>保存</Button></div> :
            (enableEdit ? <Button icon="edit" type="primary" onClick={this.handleEditUser.bind(this)}>修改</Button> : '')
        }
      </div>
    </CommonSpin>);
  }
}
