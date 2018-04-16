/**
 * Created by mosesc on 03/30/18.
 */
import React from 'react';
import {Input, Icon} from 'antd';

export default class TipEditableInput extends React.Component{
  render(){
    const {enableEdit, editing, handleChangeValueSave,
      addonBefore, value, handleEditSaveClick, handleCancelSave,
      createNew, handleChangeValue} = this.props;
    const condition = (enableEdit && editing) || createNew;
    const addonAfter = (enableEdit||createNew) ? <Icon style={{cursor: 'pointer'}} type={editing ? "save" : "edit" } onClick={handleEditSaveClick}/> : '';
    var disabled = true;
    if (condition){
      disabled = false;
    }
    return(<div style={{margin:'0 auto'}}>
      <Input addonBefore={addonBefore}
             ref="roleName"
             addonAfter={addonAfter}
             defaultValue={value}
             onChange={handleChangeValue}
             onBlur = {handleCancelSave}
             disabled={disabled} style={{width:'25%'}}
             onPressEnter={handleChangeValueSave}/>
    </div>);
  }
}
