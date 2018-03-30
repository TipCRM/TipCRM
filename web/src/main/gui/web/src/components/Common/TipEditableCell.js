/**
 * Created by mosesc on 03/30/18.
 */
import React from 'react';
import {Input, Icon} from 'antd';

export default class TipEditableCell extends React.Component{
  render(){
    const {enableEdit, editing, handleChangeValueSave, addonBefore, value, style} = this.props;
    const addonAfter = enableEdit ? <Icon type={editing ? "setting" : "edit" } /> : '';
    var disabled = true;
    if (enableEdit && editing){
      disabled = false;
    }
    return(<div style={{margin:'0 auto'}}>
      <Input addonBefore={addonBefore}
             addonAfter={addonAfter}
             defaultValue={value}
             disabled={disabled} style={{width:'25%'}}/>
    </div>);
  }
}
