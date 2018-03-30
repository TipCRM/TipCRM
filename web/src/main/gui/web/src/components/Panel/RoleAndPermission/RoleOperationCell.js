/**
 * Created by mosesc on 03/30/18.
 */
import React from 'react';
import {Button} from 'antd';
import styles from './Index.css';

export default class RoleOperationCell extends React.Component{
  render(){
    const {showEdit, showDelete, handleEditClick, handleDeleteClick} = this.props;
    return(<div>
      {showEdit ? <Button size="small" icon="edit" shape="circle" onClick={handleEditClick}/> : ''}
      {showDelete ? <Button size="small" icon="delete" type="danger" shape="circle" onClick={handleDeleteClick} className={styles.operationButton}/> : ''}
    </div>);
  }
}
