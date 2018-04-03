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
import styles from './Index.css';

@connect(({loading, permission, department}) =>({
  loadingPermission:loading.models.permission,
  menuPermissions: permission.menuPermissions,
  loading: loading.models.department,
  departments: department.departments,
}))
export default class DepartmentManagePanel extends React.Component{
  state={
    createNew: false,
    selectDepartment: {},
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
    let newDepartment = {id: '--', editing: true, createNew: true};
    departments.push(newDepartment);
    dispatch({
      type: 'department/saveDepartments',
      payload: departments,
    });
    this.setState({
      createNew: true,
    });
  }

  handleSaveDepartment(e, item, field){
    const value = e.target.defualtValue();
    const {dispatch} = this.props;
    if (item.id){
      dispatch({
        type: 'department/createNewDepartment'
      });
    }
  }

  render(){
    const {menuPermissions, departments, loading, loadingPermission} = this.props;
    // init permissions
    const {permissions} = menuPermissions;
    const enableAdd = permissions.filter(item => item === getPermission('DEPARTMENT_ADD')).length > 0;
    const enableEdit = permissions.filter(item => item === getPermission('DEPARTMENT_EDIT')).length > 0;
    const enableDelete = permissions.filter(item => item === getPermission('DEPARTMENT_DELETE')).length > 0;

    let columns = [
      {title: '部门编号', dataIndex:'id'},
      {title: '部门名称', dataIndex:'name', render:((text, item, index) =>
        (<TipEditableCell editing={item.editing}
                          enableEdit={enableEdit} value={item.name}
                          createNew = {item.createNew}
                          handleSaveValue = {this.handleSaveDepartment.bind(this, item, 'name')}/>))},
      {title: '部门经理', dataIndex:'manager.name', render:((text, item, index) =>
        (<TipEditableCell editing={item.editing} enableEdit={enableEdit}
                          value={item.manager.name} createNew = {item.createNew}
                          handleSaveValue = {this.handleSaveDepartment.bind(this, item, 'manager.name')}/>))},
      {title: '部门人数', dataIndex: 'total'},
      {title: '创建人', dataIndex:'entryUser'},
      {title: '创建时间', dataIndex:'entryTime'},
    ]

    return(<div>
      <CommonSpin spinning={loading}>
      <SearchTable
        tableClass = {styles.tableClass}
        tableColumns={columns}
        tableData={departments}
        tableRowKey = "id"
        tablePagination={false}
      />
        <CommonSpin spinning={loadingPermission}>{
          enableAdd ? <Button
            style={{ width: '60%', marginTop: 8, marginBottom: 16, marginLeft:'20%' }}
            type="dashed"
            onClick={this.handleDepartmentAdd.bind(this)}
            icon="plus"
          >
            新增部门
          </Button> : <div></div>
        }
      </CommonSpin>
    </CommonSpin></div>);
  }
}
