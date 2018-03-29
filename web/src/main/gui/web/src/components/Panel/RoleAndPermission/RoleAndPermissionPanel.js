/**
 * Created by mosesc on 03/29/18.
 */
import React from 'react';
import {connect} from 'dva';
import {Modal, Button} from 'antd';
import CommonSpin from '../../../components/Common/CommonSpin';
import SearchTable from '../../../components/Common/SearchTable';
import PermissionPanel from './PermissionPanel';
import styles from './Index.css'

@connect(({loading, role}) =>({
  loading: loading.models.role,
  roles: role.roles,
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
  }

  handlePermissionEdit(record){
    console.log(record);
    this.setState({
      showPermissionPanel: true,
      selectRole: record,
    });
  }

  handleClosePermissionPanel(){
    this.setState({
      showPermissionPanel: false,
      selectRole: {},
    });
  }

  render(){
    const {loading, roles} = this.props;
    const {selectRole, showPermissionPanel} = this.state;
    const columns = [
      {title:'角色编号', dataIndex:'id'},
      {title:'角色名称', dataIndex:'displayName'},
      {title:'创建人', dataIndex:'entryUser'},
      {title:'创建时间', dataIndex:'entryDatetime'},
      {title:'操作',render:(record =><Button onClick={this.handlePermissionEdit.bind(this, record)}>Test</Button>)},
    ];
    return(<div>
      <CommonSpin spinning={loading}>
        <SearchTable
          tableClass = {styles.table}
          tableColumns={columns}
          tableData={roles}
          tableRowKey = "id"
          tablePagination={false}
        />
      </CommonSpin>
      <Modal visible={showPermissionPanel} title="权限" footer="" width="60%"
             destroyOnClose={true} onCancel={this.handleClosePermissionPanel.bind(this)}
      >
        <PermissionPanel role={selectRole} canEdit={true} canConfigPermission={true}/>
      </Modal>
    </div>);
  }
}
