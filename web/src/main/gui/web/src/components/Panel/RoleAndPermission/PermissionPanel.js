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

@connect(({loading, permission, role}) =>({
  loading: loading.models.permission,
  roleLoading: loading.models.role,
  permissions: permission.permissions,
  selectRole: role.selectRole,
  createNew: role.createNew,
}))
export default class PermissionPanel extends React.Component{
  state={
    editingRole: this.props.createNew,
    editingPermission: false,
  };
  componentDidMount(){
    const {dispatch, selectRole} = this.props;
    if (selectRole.id){
      dispatch({
        type: 'permission/listRolePermission',
        payload: {id: selectRole.id}
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
    const {editingPermission} = this.state;
    if (editingPermission){
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
      this.setState({
        editingPermission: false,
      });
    } else {
      this.setState({
        editingPermission: true,
      });
    }
  }

  handleSaveRoleChange(createNew, selectRole, e){
    if (!this.state.editingRole){
      this.setState({
        editingRole: true,
      });
    } else {
      const {dispatch} = this.props;
      if (createNew){
        dispatch({
          type: 'role/createNewRole',
          payload: {name: e.target.value}
        });
      } else {
        dispatch({
          type: 'role/changeRole',
          payload: {selectRole: selectRole, newName: e.target.value}
        });
      }
      this.setState({
        editingRole: false,
      });
    }
  }

  render(){
    const {selectRole, enableEdit, loading, permissions, createNew, roleLoading} = this.props;
    var {editingRole, editingPermission} = this.state;

    return(<CommonSpin spinning={loading}>
      <div className={styles.permissionPanel}>
        <CommonSpin spinning={roleLoading}>
          <Row><TipEditableCell
            handleEditSaveClick = {this.handleSaveRoleChange.bind(this, createNew, selectRole)}
            addonBefore="角色名称"
            value={selectRole.displayName}
            enableEdit={enableEdit}
            createNew = {createNew}
            editing={editingRole}
            handleChangeValueSave={this.handleSaveRoleChange.bind(this, createNew, selectRole)}
            style={{with:'25px'}}/></Row>
        </CommonSpin>
        {
          createNew ? <div></div> : <div>
            <Row style={{marginBottom: '8px'}}>
              {enableEdit ? <Button
                shape="circle"
                style={{float: 'right'}}
                size="small" icon={editingPermission ? 'save' : 'edit'}
                onClick={this.handleSaveChange.bind(this, selectRole, permissions, createNew)}/> : <div></div>}
            </Row>
            <div style={{border:'1px solid', borderRadius:'2px', lineHeight:'15px'}}>
              {
                permissions ? permissions.map(item =>
                {
                  const newPermissions = item.permissions ? item.permissions.map(permission =>
                  {
                    return {...permission, handleTagChange: (enableEdit && editingPermission) ? this.handlePermissionChecked.bind(this, item, permission, permissions) : null};
                  }): [];
                  const newItem = {...item, permissions: newPermissions};
                  return  <PermissionCell permission={newItem}/>;
                }) : []
              }
            </div>
          </div>
        }
      </div>
    </CommonSpin>);
  }
}
