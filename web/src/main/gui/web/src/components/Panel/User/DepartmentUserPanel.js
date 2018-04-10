/**
 * Created by mosesc on 04/02/18.
 */
import React from 'react';
import {connect} from 'dva';
import SearchTable from '../../Common/SearchTable';
import CommonSpin from '../../Common/CommonSpin';
import styles from './Index.css';
import {} from 'antd';

@connect(({user, loading})=>
  ({
    departmentUsers: user.departmentUsers,
    userLoading: loading.models.user,
  }))
export default class DepartmentUserPanel extends React.Component{
  componentDidMount(){
    const {dispatch} = this.props;
    dispatch({
      type: 'user/listDepartmentUsers'
    });
  }
  render(){
    const {departmentUsers, userLoading} = this.props;
    const columns = [
      {title: '员工编号', dataIndex: 'id'},
      {title: '员工名', dataIndex: 'name'},
      {title: '联系电话', dataIndex: 'phone'},
      {title: '邮箱', dataIndex: 'email'},
      {title: '状态', dataIndex: 'status'}];
    return(<CommonSpin spinning = {userLoading}>
      <SearchTable tableColumns = {columns} tableData={departmentUsers.data} tableClass = {styles.tableClass}/>
    </CommonSpin>);
  }
}
