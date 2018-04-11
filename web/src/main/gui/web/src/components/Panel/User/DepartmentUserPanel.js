/**
 * Created by mosesc on 04/02/18.
 */
import React from 'react';
import {connect} from 'dva';
import SearchTable from '../../Common/SearchTable';
import CommonSpin from '../../Common/CommonSpin';
import {getPermission} from '../../../utils/PermissionConstant';
import styles from './Index.css';
import {Modal} from 'antd';
import UserDetailInfoPanel from './UserDetailInfoPanel';

@connect(({user, loading, permission})=>
  ({
    departmentUsers: user.departmentUsers,
    userLoading: loading.effects['user/listDepartmentUsers'],
    menuPermissions: permission.menuPermissions,
    permissionsLoading: loading.effects['permission/getPermissionsByMenu'],
  }))
export default class DepartmentUserPanel extends React.Component{
  state={
    showUserDetail: false,
    selectUser: {},
    createNew: false,
  }
  componentDidMount(){
    const {dispatch, menuId} = this.props;
    dispatch({
      type: 'permission/getPermissionsByMenu',
      payload: {menuId: menuId}
    });
    dispatch({
      type: 'user/listDepartmentUsers'
    });
  }
  handleTableRowSelect(record){
    return {
      onDoubleClick: () => {
        console.log("record:", record);
        this.setState({
          showUserDetail: true,
          selectUser: record,
        });
      }
    };
  }
  handleCloseUserInfo(){
    this.setState({
      showUserDetail: false,
      selectUser: {},
    });
  }
  render(){
    const {departmentUsers, userLoading, menuPermissions} = this.props;
    const {showUserDetail, selectUser, createNew} = this.state;
    //init permissions
    const {permissions} = menuPermissions;
    const enableView = permissions.filter(item => item === getPermission('USER_DEPARTMENT_VIEW')).length > 0;
    const enableEdit = permissions.filter(item => item === getPermission('USER_UPDATE')).length > 0;
    const enableAdd = permissions.filter(item => item === getPermission('USER_ADD')).length > 0;
    const enableDelete = permissions.filter(item => item === getPermission('USER_DELETE')).length > 0;
    const enableAasign = permissions.filter(item => item === getPermission('ROLE_ASSIGN')).length > 0;

    const columns = [
      {title: '员工编号', dataIndex: 'id'},
      {title: '员工名', dataIndex: 'name'},
      {title: '联系电话', dataIndex: 'phone'},
      {title: '邮箱', dataIndex: 'email'},
      {title: '状态', dataIndex: 'status'}];
    return(<div>
      <CommonSpin spinning = {userLoading}>
        <SearchTable tableColumns = {columns}
                     tableData={departmentUsers.data} tableClass = {styles.tableClass}
                     tableRowKey = 'id' onTableRow = { this.handleTableRowSelect.bind(this)}/>
      </CommonSpin>
      {
        enableView ? <Modal footer="" visible={showUserDetail} title={selectUser.name} width="60%"
                            onCancel={this.handleCloseUserInfo.bind(this)} destroyOnClose>
          <UserDetailInfoPanel selectUser={selectUser} createNew={createNew}/>
        </Modal> : ""
      }
    </div>);
  }
}
