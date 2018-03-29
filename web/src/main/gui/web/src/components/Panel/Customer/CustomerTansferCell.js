/**
 * Created by mosesc on 03/28/18.
 */
import React from 'react';
import {Select, Button} from 'antd';
const {Option} = Select;

export default class CustomerTansferCell extends React.Component{
  render(){
    const {options, handleSelectOnChange, handleStartTransfer, enable} = this.props;
    if (!enable){
      return(<div></div>);
    }
    return enable ? (<div>选择转移用户
        <Select size="small" onChange={handleSelectOnChange} defaultValue ={options[0].name} >
          {
            options.map(item => {
              return (<Option value={item.name}>{item.title}</Option>);
            })
          }
        </Select>
        <Button size="small" onClick={handleStartTransfer}>转移</Button>
      </div>) : (<div></div>);
  }
}
