/**
 * Created by Administrator on 2018/4/3.
 */
import React from 'react';
import {Select} from 'antd';
import CommonSpin from './CommonSpin';
const {Option} = Select;

export default class TipEditableSelectCell extends React.Component{
  render(){
    const {enableEdit, editing, selectData, createNew,
      handleOnSearch, handleValueChange, handleOptionSelect,
      data,fetching} = this.props;
    const selectCell = (<Select
      style={{width: '100%'}}
      mode="tags"
      labelInValue={false}
      placeholder="输入用户名"
      notFoundContent={fetching? <CommonSpin spinning={fetching}/>: ''}
      filterOption={false}
      onSearch={handleOnSearch}
      onSelect={handleOptionSelect}
      onChange={handleValueChange}>
      {data.map(item => <Option value={item.id+""}>{item.name}</Option>)}
    </Select>);

    if (createNew){
      return selectCell;
    }
    const editPanel = editing ? selectCell: (selectData ? selectData.name : '');
    return(<div>
      {enableEdit ? editPanel: (selectData ? selectData.name : '')}
    </div>);
  }
}
