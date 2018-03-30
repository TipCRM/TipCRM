/**
 * Created by mosesc on 03/29/18.
 */
import React from 'react';
import {connect} from 'dva';
import {Button, Row, Divider} from 'antd';
import styles from './Index.css';
import PermissionCell from './PermissionCell';
import CommonSpin from '../../Common/CommonSpin';
import TipEditableCell from '../../Common/TipEditableCell';

@connect(({loading, permission}) =>({
  loading: loading.models.permission,
  permissions: permission.permissions,
}))
export default class PermissionPanel extends React.Component{
  state={
    editingRole: false,
  };
  componentDidMount(){
    const {dispatch, role} = this.props;
    if (role){
      dispatch({
        type: 'permission/listRolePermission',
        payload: {id: role.id}
      });
    }
  }

  handlePermissionChecked( item, permission, permissions, checked){
    let newPermissions = permissions.map(item1 =>{
      if (item1.id === item.id){
        let permission1 = item1.permissions;
        let newPermissions1 = permission1.map(permission1 => {

          if (permission1.id === permission.id){
            var newPermission1 = {...permission1, checked: checked};
            return newPermission1;
          }
          return permission1;
        });
        return {...item1, permissions: newPermissions1};
      }
      return item1;
    });
    // change the state
    const {dispatch} = this.props;
    dispatch({
      type: 'permission/saveRolePermissions',
      payload: newPermissions,
    });
  }

  handleSaveChange(role, permissions){
    const {dispatch} = this.props;
    var permissionsArray = [];
    if (permissions){
      permissions.map(item => {
        item.permissions.map(permission =>{
          if (permission.checked){
            permissionsArray.push(permission.id)
          }
        })
      })
    }
    dispatch({
      type:'permission/changeRolePermissions',
      payload: {id: role.id, permissions: permissionsArray},
    });
  }

  render(){
    const {role, enableEdit, loading, permissions} = this.props;
    return(<CommonSpin spinning={loading}>
      <div className={styles.permissionPanel}>
        <Row><TipEditableCell addonBefore="角色名称" value={role.displayName} enableEdit={enableEdit} editing={this.state.editingRole} style={{with:'25px'}}/></Row>
        <Row style={{marginBottom: '8px'}}>
          {enableEdit ? <Button shape="circle" style={{float: 'right'}} size="small" icon="setting" onClick={this.handleSaveChange.bind(this, role, permissions)}/> : <div></div>}
        </Row>
        <div style={{border:'1px solid', borderRadius:'2px', lineHeight:'15px'}}>
          {
            permissions ? permissions.map(item =>
            {
              const newPermissions = item.permissions ? item.permissions.map(permission =>
                {
                  return {...permission, handleTagChange: enableEdit ? this.handlePermissionChecked.bind(this, item, permission, permissions) : null};
                }): [];
              const newItem = {...item, permissions: newPermissions};
              return  <PermissionCell permission={newItem}/>;
            }) : []
          }
        </div>
      </div>
    </CommonSpin>);
  }
}
