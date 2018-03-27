/**
 * Created by mosesc on 03/21/18.
 */
import React from 'react';
import {connect} from 'dva';
import CommonTab from '../../Common/CommonTab';
import SearchTable from '../../Common/SearchTable';
import CustomerDetailPanel from './CustomerDetailPanel';
import CustomerInfoCell from './CustomerInfoCell';
import {Badge} from 'antd';
import CustomerSearchCell from './CustomerSearchCell';
import CustomerContactCell from './CustomerContactCell';
import CommonSpin from '../../Common/CommonSpin';

@connect(({loading, customer}) =>({
  loading: loading.models.customer,
  customer,
}))
export default class CustomerPanel extends React.Component{
  state={
    showCustomerDetail: false,
    customer: {},
    currentPage: 1,
    pageSize: 5,
    filterCondition:{"criteria":[]},
    sorterCondition:{sort:{direction:'DESC', fieldName:'customer_name'}},
    showModal: false,
    statusFilters: [],
    onlyIntentCustomerTagChecked: false,
    onlyNewCustomerTagChecked: false,
    orderByNextContactTagChecked: false,
  };

  componentDidMount(){
    const {dispatch} = this.props;
    const {currentPage, pageSize, filterCondition, sorterCondition} = this.state;
    let pageCondition = {page: currentPage, size: pageSize};
    let request = {...pageCondition, ...filterCondition, ...sorterCondition};
    dispatch({
      type:'customer/myCustomers',
      payload:request,
    });
  }

  onTableRow(record){
    console.log(record);
    return{
      onDoubleClick:() =>{
        this.setState({
          showCustomerDetail: true,
          customer: record,
        });
      }
    };
  }
  handlerDetailCancel(){
    this.setState({
      showCustomerDetail: false,
    });
  }
  handlerOnSearch(value){
    let condition = {
        "conjunction": "AND",
        "fieldName": "customer_name",
        "method": "LIKE",
        "value": value};
    var { sorterCondition, currentPage, pageSize, filterCondition} = this.state;
    filterCondition['criteria'] = filterCondition['criteria'].filter(item => item.fieldName != 'customer_name');
    filterCondition['criteria'].push(condition);
    const pageCondition = {page: currentPage, size: pageSize};
    let request = {...filterCondition, ...pageCondition, ...sorterCondition};
    this.props.dispatch({
      type:'customer/myCustomers',
      payload:request,
    });
    this.setState({
      filterCondition: filterCondition,
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
        "fieldName": 'status',
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
      type:'customer/myCustomers',
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

  handleOnlyShowNewCustomerTagChange(checked){
    var {currentPage, pageSize, sorterCondition, filterCondition} = this.state;
    const {dispatch} = this.props;
    let pageCondition = {page: currentPage, size: pageSize};
    if (checked){
      let condition = {
        "conjunction": "AND",
        "fieldName": 'status',
        "method": "EQUALS",
        "value": [1]};
      filterCondition['criteria'].push(condition);
    } else {
      filterCondition['criteria'] = filterCondition['criteria'].filter(item => item.fieldName != 'status' && item.value[0] === 1);
    }
    const request = {...filterCondition, ...pageCondition, ...sorterCondition};
    dispatch({
      type:'customer/myCustomers',
      payload:request,
    });

    this.setState({
      onlyNewCustomerTagChecked: checked,
      filterCondition: filterCondition,
    });
  }

  handleOnlyShowIntentCustomerTagChange(checked){
    var {currentPage, pageSize, sorterCondition, filterCondition} = this.state;
    const {dispatch} = this.props;
    let pageCondition = {page: currentPage, size: pageSize};
    if (checked){
      let condition = {
        "conjunction": "AND",
        "fieldName": 'status',
        "method": "EQUALS",
        "value": [2]};
      filterCondition['criteria'].push(condition);
    } else {
      filterCondition['criteria'] = filterCondition['criteria'].filter(item => item.fieldName != 'status' && item.value[0] === 2);
    }
    const request = {...filterCondition, ...pageCondition, ...sorterCondition};
    dispatch({
      type:'customer/myCustomers',
      payload:request,
    });

    this.setState({
      onlyIntentCustomerTagChecked: checked,
      filterCondition: filterCondition,
    });
  }

  handleOrderByNextContactTagChange(checked){
    var {currentPage, pageSize, sorterCondition, filterCondition} = this.state;
    if (checked){
      sorterCondition.sort = {direction:'DESC', fieldName:'next_communication_time'};
    } else {
      sorterCondition.sort = {direction:'DESC', fieldName:'customer_name'};
    }
    const {dispatch} = this.props;
    let pageCondition = {page: currentPage, size: pageSize};
    const request = {...filterCondition, ...pageCondition, ...sorterCondition};
    dispatch({
      type:'customer/myCustomers',
      payload:request,
    });
    this.setState({
      orderByNextContactTagChecked: checked,
      sorterCondition: sorterCondition,
    });
  }

  render(){
    const columns = [{title:'公司名称',dataIndex:'customerName',key:'customerId',width: '10%',},
                      {title:'联系人',dataIndex:'contactName',},
                      {title:'联系电话',dataIndex:'contactPhone',width: '11%',},
                      {title:'最后一次沟通时间',dataIndex:'lastCommunicationTime',width: '14%',},
                      {title:'拜访记录',dataIndex:'lastCommunicationContent', width:'15%'},
                      {title:'下次沟通',dataIndex:'nextCommunicationTime',width: '14%',},
                      {title:'客户状态',dataIndex:'status',width: '10%'},
                      {title:'意向金额', dataIndex:'intentionalAmount'},
                      {title:'签约金额', dataIndex:'signAmount'},
                      {title:'操作', dataIndex:'operation',width: '8%'}];
    //todo get the columns from remote
    const {customer, loading, children, tableColumns} = this.props;
    const {currentPage, pageSize, onlyIntentCustomerTagChecked, onlyNewCustomerTagChecked, orderByNextContactTagChecked} = this.state;
    const {data, totalElements} =customer.customers;
    const tablePagination ={defaultCurrent:1, pageSize: pageSize, current: currentPage,
      total: totalElements,pageSizeOptions:['5','10','20'], showSizeChanger:{},};
    const tags = [
      {content: "意向客户", checked: onlyIntentCustomerTagChecked, handleTagChange: this.handleOnlyShowIntentCustomerTagChange.bind(this)},
      {content: "新客户", checked: onlyNewCustomerTagChecked, handleTagChange: this.handleOnlyShowNewCustomerTagChange.bind(this)},
      {content: "下一次沟通排序", checked: orderByNextContactTagChecked, handleTagChange: this.handleOrderByNextContactTagChange.bind(this)}];

    const searchContent =(<CustomerSearchCell onQuickSearch={this.handlerOnSearch.bind(this)} tags={tags}/>);

    const panels = children ? children.map(item => {
                  if (item.title === '我的所有客户'){
                    const selectCustomer = this.state.customer;
                    //init customerDetail Info
                    const customerDetailPanels =item.children ? item.children.map(panel =>{
                      if (panel.title === '客户资料'){
                        return{...panel, content:(<CustomerInfoCell customerId={selectCustomer.customerId}/>)};
                      } else if (panel.title === '联系人信息'){
                        return{...panel, content:(<CustomerContactCell/>)};
                      }
                        return {title:(<Badge count={5} dot>{panel.title}</Badge>), key:panel.key,content: panel.title};
                    }) : item.children;
                    //init customer panel
                    return {...item, content:(<div>
                      <CustomerDetailPanel visible={this.state.showCustomerDetail}
                                           title={this.state.customer.customerName}
                                           panels={customerDetailPanels}
                                           onCancel={this.handlerDetailCancel.bind(this)}/>
                      <CommonSpin spinning={loading}>
                      <SearchTable
                        tableColumns={columns}
                        tableData={data}
                        searchContent={searchContent}
                        tableFooter={()=>(<div style={{textAlign:'center'}}>累计成交金额：10,000元  意向金额：1,000元</div>)}
                        onTableRow={this.onTableRow.bind(this)}
                        tablePagination={tablePagination}
                        onTableChange={this.handlerTableChange.bind(this)}/>
                    </CommonSpin></div>)};
                  }
                  return item;
              }) : children;
    return(
      <div>
          <CommonTab panels={panels}/>
      </div>
    );
  }
}
