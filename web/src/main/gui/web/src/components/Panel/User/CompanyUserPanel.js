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
import DepartmentUserSearchCell from './CompanyUserSearchCell';

@connect(({user, loading, permission})=>
  ({
    companyUsers: user.companyUsers,
    userLoading: loading.effects['user/listCompanyUsers'],
    menuPermissions: permission.menuPermissions,
    permissionsLoading: loading.effects['permission/getPermissionsByMenu'],
  }))
export default class CompanyUserPanel extends React.Component{
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
    advanceSearch: false,
  }
  componentDidMount(){
    const {dispatch, menuId} = this.props;
    const {currentPage, pageSize, filterCondition, sorterCondition} = this.state;
    let pageCondition = {page: currentPage, size: pageSize};
    let request = {...pageCondition, ...filterCondition, ...sorterCondition};
    dispatch({
      type: 'permission/getPermissionsByMenu',
      payload: {menuId: menuId}
    });
    dispatch({
      type: 'user/listCompanyUsers',
      payload: request,
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
  handleTableChange(pagination, filters, sorter){
    const {dispatch} = this.props;
    /** init filter condition **/
    let filterCondition = {};
    if (JSON.stringify(filters) == '{}'){
      filterCondition = this.state.filterCondition;
    } else {
      filterCondition = {"criteria": [{
        "conjunction": "AND",
        "fieldName": 'status',
        "method": "EQUALS",
        "value": filters.status
      }],};
    }
    /** init sorter condition **/
    let field = sorter.field;
    let sorterCondition = {};
    if (field == null){
      sorterCondition = this.state.sorterCondition;
    } else {
      let sortOrder = sorter.order == 'ascend' ? 'ASC':'DESC';
      sorterCondition = {sort:{direction:sortOrder, fieldName: field}};
    }
    /** init page condition **/
    let pageCondition = {page: pagination.current, size: pagination.pageSize};
    /** init request **/
    let request = {...filterCondition, ...pageCondition, ...sorterCondition};
    dispatch({
      type:'user/listCompanyUsers',
      payload:request,
    });
    /** change state **/
    this.setState({
      currentPage: pagination.current,
      pageSize: pagination.pageSize,
      filterCondition: filterCondition,
      sorterCondition: sorterCondition
    });
  }

  handlerOnSearch(value){
    var { sorterCondition, currentPage, pageSize, filterCondition} = this.state;
    filterCondition['criteria'] = filterCondition['criteria'].filter(item => item.fieldName != 'user_name');
    if (value && value != ""){
      let condition = {
        "conjunction": "AND",
        "fieldName": "user_name",
        "method": "LIKE",
        "value": value};
      filterCondition['criteria'].push(condition);
    }
    const pageCondition = {page: currentPage, size: pageSize};
    let request = {...filterCondition, ...pageCondition, ...sorterCondition};
    this.props.dispatch({
      type:'user/listCompanyUsers',
      payload:request,
    });
    this.setState({
      filterCondition: filterCondition,
    });
  }
  handleSearchStatusChange(){
    this.setState({
      advanceSearch: !this.state.advanceSearch,
    });
  }

  render(){
    const {companyUsers, userLoading, menuPermissions} = this.props;
    const {showUserDetail, selectUser, createNew, advanceSearch} = this.state;
    //init permissions
    const {permissions} = menuPermissions;
    const enableView = permissions.filter(item => item === getPermission('USER_DEPARTMENT_VIEW')).length > 0;
    const enableEdit = permissions.filter(item => item === getPermission('USER_UPDATE')).length > 0;
    const enableAdd = permissions.filter(item => item === getPermission('USER_ADD')).length > 0;
    const enableDelete = permissions.filter(item => item === getPermission('USER_DELETE')).length > 0;
    const enableAasign = permissions.filter(item => item === getPermission('ROLE_ASSIGN')).length > 0;

    const tags = [{content: '在职员工',checked: false, handleTagChange: {}},
      {content: '部门员工',checked: true, handleTagChange: {}},];
    const searchCell = (<DepartmentUserSearchCell
      searchByName={this.handlerOnSearch.bind(this)}
      enableAdd={enableAdd} createNewUser={this.handleCreateNewUser.bind(this)}
      tags={tags} onChangeSearchStatus={this.handleSearchStatusChange.bind(this)}
      advanceSearch={advanceSearch}/>);

    const columns = [
      {title: '员工编号', dataIndex: 'id'},
      {title: '员工名', dataIndex: 'name'},
      {title: '部门', dataIndex: 'department'},
      {title: '联系电话', dataIndex: 'phone'},
      {title: '邮箱', dataIndex: 'email'},
      {title: '员工状态', dataIndex: 'status'}];
    const tablePagination ={defaultCurrent:1, pageSize: companyUsers.size,
      current: companyUsers.page, total: companyUsers.totalElements,pageSizeOptions:['5','10','20'],
      showSizeChanger: true,};
    return(<div>
      <CommonSpin spinning = {userLoading}>
        <SearchTable tableColumns = {columns} searchContent = {searchCell}
                     tableData={companyUsers.data} tableClass = {styles.tableClass}
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
