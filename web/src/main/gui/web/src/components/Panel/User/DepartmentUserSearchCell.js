/**
 * Created by mosesc on 04/12/18.
 */
import React from 'react';
import {Input, Button} from 'antd';
import TipCheckableTag from '../../Common/TipCheckableTag';
import CommonTagGroup from '../../Common/CommonTagGroup';
import styles from './Index.less';
const {Search} = Input;

export default class DepartmentUserSearchCell extends React.Component{
  render(){
    const {searchByName, enableAdd, createNewUser, tags} = this.props;
    return(<div>
      <div  style={{textAlign: 'center', marginBottom: '8px', marginTop:'16px'}}>
        <Search enterButton onSearch={searchByName} className={styles.searchByName} placeholder="输入员工名"/>
      </div>
      <div  className={styles.searchByStatus}>
        <CommonTagGroup tags={tags}/>
        {
          enableAdd ? <Button type="primary" icon="plus" style={{marginLeft: '30%'}} size="small" onClick={createNewUser}>添加员工</Button> :''
        }
      </div>
    </div>)
  }
}
