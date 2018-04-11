/**
 * Created by mosesc on 04/11/18.
 */
import React from 'react';
import CommonSpin from '../../Common/CommonSpin';
import {connect} from 'dva';
import {Input} from 'antd';

@connect(({loading, user})=>({
  userLoading: loading.models.user,
  selectUserInfo: user.selectUserInfo
}))
export default class UserDetailInfoPanel extends React.Component{
  componentDidMount(){
    const {dispatch, selectUser} = this.props;
    dispatch({
      type: 'user/getUserDetailInfo',
      payload: {userId: selectUser.id},
    });
  }
  render(){
    const {selectUserInfo, userLoading} = this.props;
    return(<CommonSpin spinning={userLoading}>
      <div>
        <Input addonBefore="用户名" value={selectUserInfo.name} disabled style={{marginBottom: '8px', width: '30%'}}/>
        <Input addonBefore="联系电话" value={selectUserInfo.phone} disabled style={{marginBottom: '8px', width: '30%', marginLeft:'3.3%'}}/>
        <Input addonBefore="邮箱" value={selectUserInfo.email} disabled style={{marginBottom: '8px', width: '30%', marginLeft:'3.3%'}}/>
        <Input addonBefore="状态" value={selectUserInfo.status} disabled style={{marginBottom: '8px', width: '30%'}}/>
        <Input addonBefore="提成比例" value={selectUserInfo.status} disabled style={{marginBottom: '8px', width: '30%', marginLeft:'3.3%'}}/>
      </div>
    </CommonSpin>);
  }
}
