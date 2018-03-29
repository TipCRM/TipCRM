/**
 * Created by mosesc on 03/23/18.
 */
import React from 'react';
import {connect} from 'dva';
import {Icon} from 'antd';
import SearchTable from '../../Common/SearchTable';
import CommonSpin from '../../Common/CommonSpin';
import CommonTab from '../../Common/CommonTab';
import NotificationSearchCell from '../Notification/NotificationSearchCell';

@connect(({loading, notification}) =>({
  loading: loading.models.notification,
  notification,
}))
export default class CustomerNotificationPanel extends React.Component{
  state={
    showCustomerDetail: false,
    customer: {},
    currentPage: 1,
    pageSize: 5,
    filterCondition:{"criteria":[]},
    sorterCondition:{},
    showModal: false,
    statusFilters: [],
    unReadTagChecked: false,
  };

  componentDidMount(){
    const {dispatch} = this.props;
    const {currentPage, pageSize, filterCondition, sorterCondition} = this.state;
    let pageCondition = {page: currentPage, size: pageSize};
    let request = {...pageCondition, ...filterCondition, ...sorterCondition};
    dispatch({
      type:'notification/listMyNotification',
      payload:request,
    });
  }

  handlerTableChange(pagination, filters, sorter){
    const {dispatch} = this.props;
    /** init filter condition **/
    let filterCondition = {};
    if (JSON.stringify(filters) == '{}'){
      filterCondition = this.state.filterCondition;
    } else {
      filterCondition = {"criteria": [{
        "conjunction": "AND",
        "fieldName": 'read_status',
        "method": "EQUALS",
        "value": filters.status
      }],};
    }
    /** init sorter condition **/
    let field = sorter.field;
    let sorterCondition = {};
    if (field == null){
      sorterCondition = this.state.sorterCondition;
    } else {
      let sortOrder = sorter.order == 'ascend' ? 'ASC':'DESC';
      sorterCondition = {sort:{direction:sortOrder, fieldName: field}};
    }
    /** init page condition **/
    let pageCondition = {page: pagination.current, size: pagination.pageSize};
    /** init request **/
    let request = {...filterCondition, ...pageCondition, ...sorterCondition};
    dispatch({
      type:'notification/listMyNotification',
      payload:request,
    });
    /** change state **/
    this.setState({
      currentPage: pagination.current,
      pageSize: pagination.pageSize,
      filterCondition: filterCondition,
      sorterCondition: sorterCondition
    });
  }

  handleNotReadTagChange(checked){
    /** init page condition **/
    var {currentPage, pageSize, sorterCondition, filterCondition} = this.state;
    const {dispatch} = this.props;
    let pageCondition = {page: currentPage, size: pageSize};
    if (checked){
      let condition = {
        "conjunction": "AND",
        "fieldName": 'read_status',
        "method": "EQUALS",
        "value": 15};
      filterCondition['criteria'].push(condition);
    } else {
      filterCondition['criteria'] = filterCondition['criteria'].filter(item => item.fieldName != 'read_status');
    }
    const request = {...filterCondition, ...pageCondition, ...sorterCondition};
    dispatch({
      type:'notification/listMyNotification',
      payload:request,
    });

    this.setState({
      unReadTagChecked: checked,
      filterCondition: filterCondition,
    });
  }

  render(){
    const columns = [
      {title:'状态', dataIndex:'status', key:'id', width:'10%', render:((text) =>{
        if (text === '已读'){
          return(<div><Icon type="folder-open" /> {text}</div>);
        }
        return(<div><Icon type="folder" style={{color:'#faad14'}}/> {text}</div>);
      })},
      {title:'主题', dataIndex:'subject', width:'20%'},
      {title:'内容', dataIndex:'content', width:'40%'},
      {title:'发送时间', dataIndex:'time', width:'20%'},
      {title:'发送人', dataIndex:'sender', width:'10%'}];
    const {notification, loading, children} = this.props;
    const {data, totalElements} = notification.notifications;
    const {pageSize, currentPage, unReadTagChecked} = this.state;

    const tags = [{content:'仅看未读', checked:unReadTagChecked, handleTagChange: this.handleNotReadTagChange.bind(this)}];
    const searchContent = (<NotificationSearchCell tags={tags}/>);
    const tablePagination ={defaultCurrent:1, pageSize: pageSize,
      current: currentPage, total: totalElements,pageSizeOptions:['5','10','20'],
      showSizeChanger:{},};

    return(<CommonSpin spinning={loading}>
      <SearchTable tableColumns={columns}
                   tableData={data}
                   searchContent={searchContent}
                   tablePagination={tablePagination}
                   onTableChange={this.handlerTableChange.bind(this)}/>
    </CommonSpin>);
  }
}
