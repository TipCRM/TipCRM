/**
 * Created by mosesc on 03/29/18.
 */
import React from 'react';
import {connect} from 'dva';
import {Modal, Button} from 'antd';
import CommonSpin from '../../../components/Common/CommonSpin';
import SearchTable from '../../../components/Common/SearchTable';
import PermissionPanel from './PermissionPanel';
import {getPermission} from '../../../utils/PermissionConstant';
import RoleOperationCell from './RoleOperationCell';
import styles from './Index.css'

@connect(({loading, role, permission}) =>({
  loading: loading.models.role,
  loadingPermission: loading.models.permission,
  roles: role.roles,
  menuPermissions: permission.menuPermissions,
}))
export default class RoleAndPermissionPanel extends React.Component{
  state = {
    showPermissionPanel: false,
    selectRole: {},
  }
  componentDidMount(){
    const {dispatch} = this.props;
    dispatch({
      type: 'role/fetchRoles'
    });
    dispatch({
      type: 'permission/getPermissionsByMenu',
      payload: {menuName:''}
    })
  }

  handlePermissionEdit(record){
    console.log(record);
    this.setState({
      showPermissionPanel: true,
      selectRole: record,
    });
  }

  handlePermissionDelete(record){}

  handleClosePermissionPanel(){
    this.setState({
      showPermissionPanel: false,
      selectRole: {},
    });
  }

  onTableRow(record){
    console.log(record);
    return{
      onDoubleClick:() =>{
        this.setState({
          showPermissionPanel: true,
          selectRole: record,
        });
      }
    };
  }

  render(){
    const {loading, roles, menuPermissions, loadingPermission} = this.props;
    const {selectRole, showPermissionPanel} = this.state;
    //init permissions
    const {permissions} = menuPermissions;
    const enableEdit = permissions.filter(item => item === getPermission('ROLE_EDIT')).length > 0;
    const enableAdd = permissions.filter(item => item === getPermission('ROLE_ADD')).length > 0;
    const enableDelete = permissions.filter(item => item === getPermission('ROLE_DELETE')).length > 0;

    var columns = [
      {title:'角色编号', dataIndex:'id'},
      {title:'角色名称', dataIndex:'displayName'},
      {title:'创建人', dataIndex:'entryUser'},
      {title:'创建时间', dataIndex:'entryDatetime'},
    ];

    if (enableEdit || enableDelete){
      const column = {title: '操作', render:(record =>
        <RoleOperationCell
          showEdit={enableEdit}
          showDelete={enableDelete}
          handleEditClick = {this.handlePermissionEdit.bind(this, record)}
          handleDeleteClick={this.handlePermissionDelete.bind(this, record)} />)};
      columns.push(column);
    }

    return(<div>
      <CommonSpin spinning={loadingPermission}>
        {
          enableAdd ? <Button style={{marginLeft: '20%', marginBottom: '8px'}} size="small" type="primary">添加角色</Button> : <div></div>
        }
      </CommonSpin>
      <CommonSpin spinning={loading}>
        <SearchTable
          tableClass = {styles.table}
          tableColumns={columns}
          tableData={roles}
          tableRowKey = "id"
          tablePagination={false}
          onTableRow = {this.onTableRow.bind(this)}
        />
      </CommonSpin>
      <Modal visible={showPermissionPanel} title="权限" footer="" width="60%"
             destroyOnClose={true} onCancel={this.handleClosePermissionPanel.bind(this)}
      >
        <PermissionPanel role={selectRole}
                         enableEdit={enableEdit}
                         canConfigPermission={true}/>
      </Modal>
    </div>);
  }
}
