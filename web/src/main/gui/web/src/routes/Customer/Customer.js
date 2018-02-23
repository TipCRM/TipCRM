/**
 * Created by mosesc on 02/02/18.
 */
import React from 'react';
import {connect} from 'dva';
import {Spin, Icon, Table, Button, Popover, List, Input} from 'antd';
import Loader from '../../components/Loader/Loader';
import styles from '../Customer/Customer.less';
import EditableItem from '../../components/EditableItem';

@connect(({customer, loading})=>
  ({customer,
    loading: loading.models.customer}))
export default class Customer extends React.PureComponent{

  state={
    filterDropdownVisible: false,
    searchText:'',
    filtered: false,
    currentPage: 1,
    pageSize: 10,
    searchConditions:{},
    sortCondition:{sort:{direction:'ASC', fieldName:'customer_name'}},
    sortOrder: false,
  }

  componentDidMount(){
    console.log('init...');
    const {dispatch} = this.props;
    let request = {page: this.state.currentPage, size: this.state.pageSize};
    request = {...request, ...this.state.searchConditions, ...this.state.sortCondition};
    dispatch({
      type:'customer/myCustomers',
      payload:request,
    });
    let {customer} = this.props;
    this.setState({
      currentPage: customer.customers.page
    });
  }

  /**
   * todo 初始化有问题，会影响整个table
   */
  initPopoverData(key){
    console.log('init popover data...');
    const {dispatch} = this.props;
    dispatch({
      type:'customer/popoverData',
      payload: [],
    });
  }

  handlerOnChange(pagination, filters, sorter){
    console.log('params', pagination, filters, sorter);
  }

  initPopover(record){
   // this.initPopoverData(record.key);
    const data = [
      {title:'2018-1-5', desc: '2018-1-5 去他家拜访'},
      {title:'2018-1-2', desc: '2018-1-2 电话开始联系'},
    ];
    return(
      <div style={{width:'150px'}}>
        <List dataSource = {data} size="small"
          renderItem={item => (
            <List.Item size="small">
              <List.Item.Meta size="small"
                description = {item.desc}
              />
            </List.Item>
          )}
        />
      </div>
    );
  }

  onInputChange = (e)=>{
    this.setState({
      searchText: e.target.value,
    });
  }

  /**
   * search the customer by name
   */
  onSearchByName = () =>{
    const {searchText} = this.state;
    const {dispatch} = this.props;
    let condtion = {"criteria": [
      {
        "conjunction": "AND",
        "fieldName": "customer_name",
        "method": "LIKE",
        "value": searchText
      }],};
    let request = {page: 1, size: this.state.pageSize};
    request = { ...condtion, ...request,...this.state.sortCondition};
    dispatch({
      type:'customer/myCustomers',
      payload:request,
    });
    this.setState({
      filterDropdownVisible: false,
      filtered:!!searchText,
      searchConditions: condtion,
    });
  }

  onPageChange = (page, pageSize) => {
    const {searchText} = this.state;
    const {dispatch} = this.props;
    dispatch({
      type:'customer/myCustomers',
      payload:{page: page, size: pageSize, sort:{direction:'ASC', fieldName:'customer_name'}},
    });
    this.setState({
      filterDropdownVisible: false,
      filtered:!!searchText,
      currentPage: page,
      pageSize: pageSize,
    });
  }

  onSizeChange = (current, size) =>{
    const {dispatch} = this.props;
    let request = {page: current, size: size};
    request = {...this.state.searchConditions, ...request, ...this.state.sortCondition};
    dispatch({
      type:'customer/myCustomers',
      payload: request,
    });
    this.setState({
      currentPage: current,
      pageSize: size,
    });
  }

  sorterByStatus =() => {
    const {dispatch} = this.props;
    let sortOrder = this.state.sortOrder == 'ascend' ? 'ASC':'DESC';
    let sortCondition = {sort:{direction:sortOrder, fieldName:'status'}};
    let request = {page: this.state.currentPage, size: this.state.pageSize};
    request = {...this.state.searchConditions, ...request, ...sortCondition};
    dispatch({
      type:'customer/myCustomers',
      payload: request,
    });
    this.setState({
      sortCondition: sortCondition,
    });
  }

  render(){
    // const loadingIcon =(<Icon type="loading" style={{ size: 30 }}/>);
    // const {loading} = this.props;
    const {loading, customer} = this.props;
    const customers = customer.customers;
    console.log(customer);
    const searchDropdown = (
      <div className="custom-filter-dropdown">
        <Input
          size="small"
          ref={ele => this.searchInput = ele}
          placeholder="输入客户名称"
          value={this.state.searchText}
          onChange={this.onInputChange}
          onPressEnter={this.onSearchByName}
          style={{width:'50%', margiRight: '12px',marginLeft: '-43px',}}
        />
        <Button size="small" type="primary" onClick={this.onSearchByName} style={{marginLeft: '3px',}}>搜索</Button>
      </div>
    );

    const columns=[
      {title:'公司名称',dataIndex:'customerName',key:'customerId',
        filterDropdown:searchDropdown,
        filterIcon:<Icon type="search" style={{ color: this.state.filtered ? '#108ee9' : '#aaa' }}/>,
        filterDropdownVisible: this.state.filterDropdownVisible,
        onFilterDropdownVisibleChange: (visible) =>{
          this.setState({
              filterDropdownVisible: visible,
          },() => this.searchInput && this.searchInput.focus()
          );
        },
      },
      {title:'联系人',dataIndex:'contactName',
      render:((text) =>{
        return (
          <EditableItem value={text}/>
        );
      }),},
      {title:'联系电话',dataIndex:'phone',
        render:((text) =>{
          return (
            <EditableItem value={text} type="contactPhone"/>
          );
      }),},
      {title:'最后一次沟通时间',dataIndex:'lastCommunicationTime',},
      {title:'拜访记录',dataIndex:'lastCommunicationContent', width:'18%',
        render:((text, record) =>{
          return(
            <Popover content={this.initPopover(record)} trigger="click" placement="bottom" >
              <div style={{cursor:'pointer'}}>{text}</div>
            </Popover>);}),className:styles.tableColumn},
      {title:'下次沟通',dataIndex:'nextCommunicationTime'},
      {title:'客户状态',dataIndex:'status', filters:[
        {text:'意向客户',value:'意向客户'},
        {text:'签约客户',value:'签约客户'},
        {text:'新客户',value:'新客户'},
        {text:'过期客户',value:'过期客户'},],
        onFilter:(value, record) => record.status.indexOf(value) === 0,
        sorter:true,
        sorterOrder: this.sorterByStatus,},
        {title:'意向金额', dataIndex:'intentionalAmount'},
        {title:'签约金额', dataIndex:'signAmount'},];

    const rowSelection = {};
    const pagination = {defaultCurrent:1, pageSize:customers.size,
                      current:this.state.currentPage, total: customers.totalElements,
                      showSizeChanger:{},
                      onShowSizeChange: this.onSizeChange,
                      onChange:this.onPageChange
                      };

    const content = (
      <div style={{marginTop:'10px',background: '#fff'}}>
          <Button style={{margin: '8px 8px 8px 8px'}} type='primary'>添加客户</Button>
          <Table style={{textAlign:'center'}} size={'small'} rowKey={(record) => record.customerId}
                 columns={columns} dataSource={customers.data}
                 rowSelection={rowSelection} bordered
                 pagination={pagination}
                 onChange={this.handlerOnChange}
          />
      </div>);
    return(
      <div >
          <Loader spinning={false} fullScreen={false}/>
          <Spin size={'default'}  tip="加载中..." style={{fontSize:14}} spinning={loading}>
            {content}
          </Spin>
      </div>
    );
  }

}
