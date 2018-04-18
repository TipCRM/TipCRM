/**
 * Created by mosesc on 04/02/18.
 */
import React from 'react';
import {connect} from 'dva';
import SearchTable from '../../Common/SearchTable';
import CommonSpin from '../../Common/CommonSpin';
import {getPermission} from '../../../utils/PermissionConstant';
import styles from './Index.less';
import {Modal} from 'antd';
import UserDetailInfoPanel from './UserDetailInfoPanel';
import DepartmentUserSearchCell from './DepartmentUserSearchCell';

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
    currentPage: 1,
    pageSize: 5,
    filterCondition:{"criteria":[]},
    sorterCondition:{},
    showModal: false,
    statusFilters: [],
    unReadTagChecked: false,
  }
  componentDidMount(){
    const {dispatch, menuId} = this.props;
    const {currentPage, pageSize} = this.state;
    dispatch({
      type: 'permission/getPermissionsByMenu',
      payload: {menuId: menuId}
    });
    dispatch({
      type: 'user/listDepartmentUsers',
      payload: {page: currentPage, size: pageSize},
    });
  }
  handleTableRowSelect(record){
    return {
      onDoubleClick: () => {
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
      createNew: false,
    });
  }
  handleCreateNewUser(){
    this.setState({
      showUserDetail: true,
      createNew: true,
    });
  }
  handleTableChange(pagination, filters, sorter){}

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

    const tags = [{content: '在职员工',checked: false, handleTagChange: {}}];
    const searchCell = (<DepartmentUserSearchCell  enableAdd={enableAdd} createNewUser={this.handleCreateNewUser.bind(this)} tags={tags}/>);

    const columns = [
      {title: '员工编号', dataIndex: 'id'},
      {title: '员工名', dataIndex: 'name'},
      {title: '联系电话', dataIndex: 'phone'},
      {title: '邮箱', dataIndex: 'email'},
      {title: '状态', dataIndex: 'status'}];
    const tablePagination ={defaultCurrent:1, pageSize: 5,
      current: 1, total: 10,pageSizeOptions:['5','10','20'],
      showSizeChanger:{},};
    return(<div>
      <CommonSpin spinning = {userLoading}>
        <SearchTable tableColumns = {columns} searchContent = {searchCell}
                     tableData={departmentUsers.data} tableClass = {styles.tableClass}
                     tableRowKey = 'id' onTableRow = { this.handleTableRowSelect.bind(this)}
                     tablePagination={tablePagination}
                     onTableChange={this.handleTableChange.bind(this)}/>
      </CommonSpin>
      {
        enableView ? <Modal footer="" visible={showUserDetail} title={createNew ? '创建新用户' : selectUser.name} width="60%"
                            onCancel={this.handleCloseUserInfo.bind(this)} destroyOnClose>
          <UserDetailInfoPanel selectUser={selectUser} createNew={createNew} enableEdit={enableEdit}/>
        </Modal> : ""
      }
    </div>);
  }
}
