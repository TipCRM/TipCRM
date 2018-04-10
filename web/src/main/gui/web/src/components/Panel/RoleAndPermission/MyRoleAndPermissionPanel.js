/**
 * Created by mosesc on 04/10/18.
 */
import React from 'react';
import {connect} from 'dva';
import {Tree, Icon} from 'antd';
import styles from './Index.css';
const {TreeNode} = Tree;

@connect(({loading, permission}) => ({
  loading: loading.models.permission,
  myPermissions: permission.myPermissions
}))
export default class MyRoleAndPermissionPanel extends React.Component{
  componentDidMount(){
    const {dispatch} = this.props;
    dispatch({
      type: 'permission/listMyPermissions',
    });
  }

  getCheckedKeys(myPermissions){
    let checkedKeys = [];
    for(let i= 0; i< myPermissions.length; i++){
      let menuPermissions = myPermissions[i].permissions;
      for (let j=0; j<  menuPermissions.length; j++){
        let permission = menuPermissions[j];
        if (permission.checked){
          checkedKeys.push(permission.id +"-permission");
        }
      }
    }
    return checkedKeys;
  }
  handleSelectTree(selectedKeys){
    console.log("selectedKeys:",selectedKeys);
  }

  render(){
    const {myPermissions} = this.props;
    const checkedKeys = this.getCheckedKeys(myPermissions);
    console.log("checked tree:", checkedKeys)
    return(<div className={styles.table}>
      <Tree showLine multiple checkable={false} selectedKeys={checkedKeys} onSelect={this.handleSelectTree.bind(this)}>
        <TreeNode title="我的菜单权限">
          {myPermissions.map(menu =>
            <TreeNode title={<div><Icon type="book" />{menu.menuName}</div>} key={menu.menuId + "-menu"}  disableCheckbox>
              {
                menu.permissions.map(permission => <TreeNode disableCheckbox title={permission.displayName} key={permission.id + "-permission"} isLeaf/>)
              }
            </TreeNode>
          )}
        </TreeNode>
      </Tree>
    </div>);
  }
}
