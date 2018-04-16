/**
 * Created by mosesc on 03/30/18.
 */
import React from 'react';
import {Button} from 'antd';
import styles from './Index.css';

export default class RoleOperationCell extends React.Component{
  render(){
    const {showEdit, showDelete, handleEditClick, handleDeleteClick, editing, handleCancelEditClick} = this.props;
    return(<div>
      {showEdit ? <Button size="small" icon={editing ? "save":"edit"} shape="circle" onClick={handleEditClick} type="primary"/> : ''}
      {(showEdit&& editing) ? <Button size="small" icon="close" shape="circle" onClick={handleCancelEditClick} className={styles.operationButton}/> : ''}
      {(showDelete && !editing) ? <Button size="small" icon="delete" type="danger" shape="circle" onClick={handleDeleteClick} className={styles.operationButton}/> : ''}
    </div>);
  }
}
