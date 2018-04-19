/**
 * Created by mosesc on 04/02/18.
 */
import React from 'react';
import {connect} from 'dva';
import {Button} from 'antd';
import {getPermission} from '../../../utils/PermissionConstant';
import CommonSpin from '../../Common/CommonSpin';
import SearchTable from '../../Common/SearchTable';
import TipEditableCell from '../../Common/TipEditableCell';
import RoleOperationCell from '../RoleAndPermission/RoleOperationCell';
import TipEditableSelectCell from '../../Common/TipEditableSelectCell';
import styles from './Index.css';

@connect(({loading, permission, department, user}) =>({
  loadingPermission:loading.models.permission,
  menuPermissions: permission.menuPermissions,
  loading: loading.models.department,
  departments: department.departments,
  loadingUsers: loading.models.user,
  users: user.users,
}))
export default class DepartmentManagePanel extends React.Component{
  state={
    createNew: false,
    selectDepartment: {},
    newDepartmentName: null,
    newManagerId: null,
    newManagerName: null,
    selectManager: null,
  }
  componentDidMount(){
    const {dispatch, menuId} = this.props;
    dispatch({
      type: 'permission/getPermissionsByMenu',
      payload: {menuId: menuId}
    });
    dispatch({
      type: 'department/listDepartments',
    });
  }

  handleDepartmentAdd(){
    let {dispatch, departments} = this.props;
    let newDepartment = {editing: true, createNew: true};
    departments.push(newDepartment);
    dispatch({
      type: 'department/saveDepartments',
      payload: departments,
    });
    this.setState({
      createNew: true,
    });
  }

  handleSaveDepartment(item){
    const {dispatch} = this.props;
    const {newDepartmentName} = this.state;
    if (item.id){
      dispatch({
        type: 'department/updateDepartment',
        payload:{id: item.id, name: newDepartmentName}
      });
    } else {
      dispatch({
        type: 'department/createNewDepartment',
        payload: {name: newDepartmentName}
      });
    }
  }
  handleDepartmentEdit(record){
    const {dispatch, departments} = this.props;
    if (record.editing){
      this.handleSaveDepartment(record);
    } else {
      let newDepartments = departments.filter(item => item.id != record.id);
      record.editing = true;
      newDepartments.push(record);
      dispatch({
        type: 'department/saveDepartments',
        payload: newDepartments,
      });
      this.setState({
        newDepartmentName:record.name,
        newManagerId: record.manager? record.manager.id: null,
        newManagerName: record.manager? record.manager.name : null,
        selectManager: record.manager,
      });
    }
  }
  handleDepartmentDelete(record){
    const {dispatch, departments} = this.props;
    dispatch({
      type: 'department/deleteDepartment',
      payload: {deleteId: record.id, departments: departments}
    });
  }
  handleCancelEditDepartment(record){
    let {dispatch, departments} = this.props;
    console.log("record:", record);
    if (record.createNew){
      departments = departments.filter(department => department.id != null);
      dispatch({
        type: 'department/saveDepartments',
        payload: departments,
      });
    } else{
      departments = departments.map(department => {
        if (department.id == record.id){
          return {...department, editing: false};
        }
        return department;
      });
      dispatch({
        type: 'department/saveDepartments',
        payload: departments,
      });
      this.setState({
        newDepartmentName: record.name,
      });
    }
  }

  handleDepartmentNameChange(e){
    this.setState({
      newDepartmentName: e.target.value,
    });
  }

  handleSelectCellOnChange(value){
    this.setState({
      newManagerName: value,
    });
  }
  handleSelectCellOnSearch(value){
    const {dispatch} = this.props;
    dispatch({
      type: 'user/getUserByName',
      payload: {userName: value ? value: ' ', includeDismiss: false},
    })
  }
  handleOptionSelect(value, key){
    console.log("value:", value, " key:", key);
    const {users} = this.props;
    const user = users.filter(user => user.id == value);
    console.log("select manager", user);
    this.setState({
      selectManager: user,
    });
  }

  render(){
    const {menuPermissions, departments, loading, loadingPermission, users, loadingUsers} = this.props;
    const {selectManager, selectDepartment} = this.state;
    // init permissions
    const {permissions} = menuPermissions;
    const enableAdd = permissions.filter(item => item === getPermission('DEPARTMENT_ADD')).length > 0;
    const enableEdit = permissions.filter(item => item === getPermission('DEPARTMENT_EDIT')).length > 0;
    const enableDelete = permissions.filter(item => item === getPermission('DEPARTMENT_DELETE')).length > 0;
    let columns = [
      {title: '部门编号', dataIndex:'id', width: '10%'},
      {title: '部门名称', dataIndex:'name', width: '15%', render:((text, item, index) =>
        <TipEditableCell editing={item.editing}
                          enableEdit={enableEdit} value={item.name}
                          createNew = {item.createNew}
                          handleValueChange = {this.handleDepartmentNameChange.bind(this)}
                          handleSaveValue = {this.handleSaveDepartment.bind(this, item)}/>)},
      {title: '部门经理', dataIndex:'manager', width: '15%', render:((text, item, index) =>
        <TipEditableSelectCell editing={item.editing} enableEdit={enableEdit} data={users}
                               selectData={item.editing? selectManager: item.manager} createNew = {item.createNew} fetching={loadingUsers}
                               handleOnSearch = {this.handleSelectCellOnSearch.bind(this)}
                               handleOptionSelect ={this.handleOptionSelect.bind(this)}
                               handleValueChange = {this.handleSelectCellOnChange.bind(this)}/>)},
      {title: '部门人数', width: '10%', dataIndex: 'total'},
      {title: '创建人', width: '20%', dataIndex:'entryUser'},
      {title: '创建时间', width: '20%', dataIndex:'entryTime'},
    ];
    if (enableEdit || enableDelete){
      const column = {title: '操作', render:(record =>
        <RoleOperationCell
          editing={record.editing}
          showEdit={enableEdit}
          showDelete={enableDelete}
          handleCancelEditClick = {this.handleCancelEditDepartment.bind(this, record)}
          handleEditClick = {this.handleDepartmentEdit.bind(this, record)}
          handleDeleteClick={this.handleDepartmentDelete.bind(this, record)} />)};
      columns.push(column);
    }

    return(<div>
      <CommonSpin spinning={loading}>
      <SearchTable
        tableClass = {styles.tableClass}
        tableColumns={columns}
        tableData={departments}
        tableRowKey = "id"
        tablePagination={false}/>
        <CommonSpin spinning={loadingPermission}>{
          enableAdd ? <Button
            style={{ width: '60%', marginTop: 8, marginBottom: 16, marginLeft:'20%' }}
            type="dashed"
            onClick={this.handleDepartmentAdd.bind(this)}
            icon="plus">
            新增部门
          </Button> : <div></div>
        }
      </CommonSpin>
    </CommonSpin></div>);
  }
}
