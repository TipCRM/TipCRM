/**
 * Created by Administrator on 2018/4/3.
 */
import React from 'react';
import {Select} from 'antd';
import CommonSpin from './CommonSpin';
const {Option} = Select;

export default class TipEditableSelectCell extends React.Component{
  render(){
    const {enableEdit, editing, selectData, createNew, handleOnSearch, handleValueChange, data, fetching} = this.props;
    if (createNew){
      return (<Select
        mode="combobox"
        value={selectData.id}
        labelInValue
        placeholder={selectData.name ? selectData.name : '请输入用户'}
        notFoundContent={<CommonSpin spinning={fetching}/>}
        filterOption={false}
        onSearch={handleOnSearch}
        onChange={handleValueChange}
      >
        {data.map(item => <Option value={item.id}>{item.name}</Option>)}
      </Select>);
    }
    const editPanel = editing ? <Select
        mode="combobox"
        value={selectData.id}
        labelInValue
        placeholder={selectData.name ? selectData.name : '请输入用户'}
        notFoundContent={<CommonSpin spinning={fetching}/>}
        filterOption={false}
        onSearch={handleOnSearch}
        onChange={handleValueChange}>
        {data.map(item => <Option value={item.id}>{item.name}</Option>)}
      </Select> : data.name;
    return(<div>
      {enableEdit ? editPanel: data.name}
    </div>);
  }
}
