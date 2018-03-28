/**
 * Created by mosesc on 03/28/18.
 */
import React from 'react';
import {Menu, Button, Dropdown, Icon} from 'antd';
const MenuItem = Menu.Item;

export default class CustomerTansferCell extends React.Component{
  render(){
    const menu = (<Menu>
      <MenuItem key="1">经理</MenuItem>
      <MenuItem key="2">小李</MenuItem>
      <MenuItem key="3">叔叔</MenuItem>
    </Menu>)
    return(<div>转移选中客户到：
      <Dropdown overlay={menu}>
        <Button style={{ marginLeft: 8 }}>
          请选择 <Icon type="down" />
        </Button>
      </Dropdown>
    </div>);
  }
}
