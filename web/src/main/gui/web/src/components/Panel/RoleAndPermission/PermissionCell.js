/**
 * Created by mosesc on 03/29/18.
 */
import React from 'react';
import TipCheckableTag from '../../Common/TipCheckableTag';

export default class PermissionCell extends React.Component{
  render(){
    const {permission} = this.props;
    return(<div>
      {permission.name}:
      {
        permission.permissions? permission.permissions.map(item =>(
          <TipCheckableTag
            tagChecked={item.checked}
            handleTagChange={item.handleTagChange}>
            {item.displayName}
          </TipCheckableTag>)):[]
      }
    </div>);
  }
}
