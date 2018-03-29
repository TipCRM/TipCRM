/**
 * Created by mosesc on 03/29/18.
 */
import React from 'react';
import {connect} from 'dva';
import {} from 'antd';
import styles from './Index.css';
import PermissionCell from './PermissionCell';
import CommonSpin from '../../Common/CommonSpin';

@connect(({loading, permission}) =>({
  loading: loading.models.permission,
  permissions: permission.permissions,
}))
export default class PermissionPanel extends React.Component{
  state={
    permissions: [],
  }
  componentDidMount(){
    const {dispatch, role} = this.props;
    if (role){
      dispatch({
        type: 'permission/listRolePermission',
        payload: {id: role.id}
      });

      this.setState({
        permissions: this.props.permissions,
      });
    }
  }

  handlePermissionChecked(checked, item, permission){
    let {permissions} = this.state;
    let newPermissions = permissions.map(item1 =>{
      if (item1.name === item.name){
        let newItem = item1.permissions.map(permission1 => {
          if (permission1.name === permission.name){
            return (permission1[checked] = checked);
          }
          return permission1;
        })
        return newItem;
      }
      return item1;
    });
    this.setState({
      permissions: newPermissions,
    });
  }

  render(){
    const {role, canEdit, canConfigPermission, loading, permissions} = this.props;
    return(<CommonSpin spinning={loading}>
      <div className={styles.permissionPanel}>
        <div>{role.displayName}</div>
        <div>
          {
            permissions ? permissions.map(item =>
            {
              const newItem = item.permissions ? item.permissions.map(permission =>
                {
                  return {...permission, handleTagChange: canEdit ? this.handlePermissionChecked.bind(this, item, permission) : {}};
                }): [];

              return  <PermissionCell permission={newItem}/>;
            }) : []
          }
        </div>
      </div>
    </CommonSpin>);
  }
}
