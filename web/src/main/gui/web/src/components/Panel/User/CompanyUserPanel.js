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

@connect(({user, loading, permission, department, level})=>
  ({
    companyUsers: user.companyUsers,
    userLoading: loading.effects['user/listCompanyUsers'],
    menuPermissions: permission.menuPermissions,
    permissionsLoading: loading.effects['permission/getPermissionsByMenu'],
    departments: department.departments,
    levels: level.levels,
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
    // for advance search
    advanceSearch: false,
    advanceCheckedAlive: false,
    advanceCheckedDead: false,
    advanceFilter: "",
    advanceSelectDepartments: [],
    // quick search
    checkMyDepartmentUsers: false,
    checkActiveUsers: false,
  }
  componentDidMount(){
    const {dispatch, menuId} = this.props;
    const {currentPage, pageSize, filterCondition, sorterCondition} = this.state;
    let pageCondition = {page: currentPage, size: pageSize};
    let request = {...pageCondition, ...filterCondition, ...sorterCondition};
    // init permission
    dispatch({
      type: 'permission/getPermissionsByMenu',
      payload: {menuId: menuId}
    });
    // init user
    dispatch({
      type: 'user/listCompanyUsers',
      payload: request,
    });
    // init department
    dispatch({
      type: 'department/listDepartments'
    });
    // init level
    dispatch({
      type: 'level/listLevels',
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
    const {sorterCondition, filterCondition} = this.state;
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
  handleSearchStatusChange() {
    const {filterCondition, sorterCondition, currentPage, pageSize, advanceSearch} = this.state;
    if (filterCondition.criteria || sorterCondition){
      let request = {page: currentPage, size: pageSize};
      this.props.dispatch({
        type:'user/listCompanyUsers',
        payload:request,
      });
      this.setState({
        filterCondition:{"criteria":[]},
        sorterCondition:{},
      });
    }
    if (advanceSearch){
      this.setState({
        advanceSearch: false,
        advanceCheckedAlive: false,
        advanceCheckedDead: false,
        advanceFilter: {},
        advanceSelectDepartments: [],
        filterCondition:{"criteria":[]},
        sorterCondition:{},
        checkMyDepartmentUsers: false,
        checkActiveUsers: false,
      });
    } else{
      this.setState({
        advanceSearch: true,
        filterCondition:{"criteria":[]},
        sorterCondition:{},
      });
    }

  }
  handleClearAdvanceCondition(){
    const {filterCondition, sorterCondition, currentPage, pageSize} = this.state;
    if (filterCondition.criteria || sorterCondition){
      let request = {page: currentPage, size: pageSize};
      this.props.dispatch({
        type:'user/listCompanyUsers',
        payload:request,
      });
      this.setState({
        filterCondition:{"criteria":[]},
        sorterCondition:{},
      });
    }
    this.setState({
      advanceCheckedAlive: false,
      advanceCheckedDead: false,
      advanceFilter: {},
      advanceSelectDepartments: [],
    });
  }
  handleDepartmentChange(value){
    this.setState({
      advanceSelectDepartments: value,
    });
  }
  handleFilterChange(e){
    this.setState({
      advanceFilter: e.target.value,
    });
  }
  handleCheckedDead(checked){
    this.setState({
      advanceCheckedDead: checked,
    });
  }
  handleCheckedAlive(checked){
    this.setState({
      advanceCheckedAlive: checked,
    });
  }
  handleAdvanceSearch(){
    let {currentPage, pageSize, advanceFilter, filterCondition,
      advanceSelectDepartments, advanceCheckedDead, advanceCheckedAlive} = this.state;
    let pageCondition = {page: currentPage, size: pageSize};
    let sorterCondition;
    if (advanceFilter != ""){
      sorterCondition = {sort:{direction:'DESC', fieldName: advanceFilter}};
    }
    let filterConditions = filterCondition['criteria'];
    if (filterConditions.length > 0){
      filterConditions = filterConditions.filter(item => item.fieldName != 'department' && item.fieldName != 'status');
    }
    if (advanceSelectDepartments.length > 0){
      filterConditions.push({
        fieldName: 'department',
        value: advanceSelectDepartments,
      });
    }
    let status = [];
    if (advanceCheckedAlive){
      status.push(10);
    }
    if (advanceCheckedDead){
      status.push(11);
    }
    console.log("dead", advanceCheckedDead);
    if (status.length > 0){
      filterConditions.push({
        fieldName: 'status',
        value: status,
      });
    }
    filterCondition['criteria'] = filterConditions;
    let request = {...filterCondition, ...pageCondition, ...sorterCondition};
    this.props.dispatch({
      type:'user/listCompanyUsers',
      payload:request,
    });
    this.setState({
      filterCondition: filterCondition,
      sorterCondition: sorterCondition,
    });
  }

  handleActiveUsers(checked){
    var { sorterCondition, currentPage, pageSize, filterCondition} = this.state;
    const {dispatch} = this.props;
    /** init page condition **/
    let pageCondition = {page: currentPage, size: pageSize};
    /** init request **/
    let request = {...filterCondition, ...pageCondition, ...sorterCondition};
    dispatch({
      type:'user/listCompanyUsers',
      payload:request,
    });
    this.setState({
      filterCondition: filterCondition,
      sorterCondition: sorterCondition,
      checkActiveUsers: checked,
    });
  }

  handleMyDepartmentUsers(checked){
    var { sorterCondition, currentPage, pageSize, filterCondition} = this.state;
    const {dispatch} = this.props;
    /** init page condition **/
    let pageCondition = {page: currentPage, size: pageSize};
    /** init request **/
    let request = {...filterCondition, ...pageCondition, ...sorterCondition};
    dispatch({
      type:'user/listCompanyUsers',
      payload:request,
    });
    this.setState({
      filterCondition: filterCondition,
      sorterCondition: sorterCondition,
      checkMyDepartmentUsers: checked,
    });
  }

  render(){
    const {companyUsers, userLoading, menuPermissions, departments, levels} = this.props;
    const {showUserDetail, createNew, selectUser, checkActiveUsers, checkMyDepartmentUsers,
      advanceSearch, advanceCheckedDead, advanceCheckedAlive, advanceFilter, advanceSelectDepartments} = this.state;
    //init permissions
    const {permissions} = menuPermissions;
    const enableView = permissions.filter(item => item === getPermission('USER_VIEW')).length > 0;
    const enableEdit = permissions.filter(item => item === getPermission('USER_UPDATE')).length > 0;
    const enableAdd = permissions.filter(item => item === getPermission('USER_ADD')).length > 0;
    const enableDelete = permissions.filter(item => item === getPermission('USER_DELETE')).length > 0;
    const enableAasign = permissions.filter(item => item === getPermission('ROLE_ASSIGN')).length > 0;

    const tags = [{content: '在职员工',checked: checkActiveUsers, handleTagChange: this.handleActiveUsers.bind(this)},
      {content: '部门员工',checked: checkMyDepartmentUsers, handleTagChange: this.handleMyDepartmentUsers.bind(this)},];
    const searchCell = (<DepartmentUserSearchCell
      searchByName={this.handlerOnSearch.bind(this)}
      enableAdd={enableAdd} createNewUser={this.handleCreateNewUser.bind(this)}
      tags={tags} onChangeSearchStatus={this.handleSearchStatusChange.bind(this)}
      advanceSearch={advanceSearch} departments={departments}
      checkedDead={advanceCheckedDead} checkedAlive={advanceCheckedAlive} onDepartmentChange={this.handleDepartmentChange.bind(this)}
      onClearAdvanceCondition={this.handleClearAdvanceCondition.bind(this)}
      onFilterChange = {this.handleFilterChange.bind(this)} filterValue={advanceFilter} selectDepartments={advanceSelectDepartments}
      onCheckedDead = {this.handleCheckedDead.bind(this)} onCheckedAlive={this.handleCheckedAlive.bind(this)}
      onAdvanceSearch={this.handleAdvanceSearch.bind(this)}/>);

    const columns = [
      {title: '员工编号', dataIndex: 'workNo'},
      {title: '员工名', dataIndex: 'name'},
      {title: '部门', dataIndex: 'department'},
      {title: '联系电话', dataIndex: 'phoneNo'},
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
                     tablePagination={tablePagination} onTableChange={this.handleTableChange.bind(this)}/>
      </CommonSpin>
      {
        enableView ? <Modal footer="" visible={showUserDetail} title={createNew ? '创建新用户' : selectUser.name} width="40%"
                            onCancel={this.handleCloseUserInfo.bind(this)} destroyOnClose>
          <UserDetailInfoPanel selectUser={selectUser} departments = {departments}
                               createNew={createNew}  levels={levels}
                               enableEdit = {enableEdit}
                               handleCancelSave = {this.handleCloseUserInfo.bind(this)}/>
        </Modal> : ""
      }
    </div>);
  }
}
