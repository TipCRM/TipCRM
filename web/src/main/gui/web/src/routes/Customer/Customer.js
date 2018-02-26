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
    filterCondition:{},
    sorterCondition:{sort:{direction:'DESC', fieldName:'customer_name'}},
  }

  componentDidMount(){
    const {dispatch} = this.props;
    let request = {page: this.state.currentPage, size: this.state.pageSize};
    request = {...request, ...this.state.filterCondition, ...this.state.sorterCondition};
    dispatch({
      type:'customer/myCustomers',
      payload:request,
    });
    let {customer} = this.props;
    this.setState({
      currentPage: customer.customers.page,
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

  handlerOnChange=(pagination, filters, sorter) => {
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
    let filterCondition = {"criteria": [
      {
        "conjunction": "AND",
        "fieldName": "customer_name",
        "method": "LIKE",
        "value": searchText
      }],};
    let request = {page: 1, size: this.state.pageSize};
    request = { ...filterCondition, ...request,...this.state.sorterCondition};
    dispatch({
      type:'customer/myCustomers',
      payload:request,
    });
    this.setState({
      filterDropdownVisible: false,
      filtered:!!searchText,
      filterCondition: filterCondition,
    });
  }

  render(){
    // const loadingIcon =(<Icon type="loading" style={{ size: 30 }}/>);
    // const {loading} = this.props;
    const {loading, customer} = this.props;
    const customers = customer.customers;
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
        {text:'意向客户',value:1},
        {text:'签约客户',value:2},
        {text:'新客户',value:3},
        {text:'过期客户',value:4},], sorter: true},
        {title:'意向金额', dataIndex:'intentionalAmount'},
        {title:'签约金额', dataIndex:'signAmount'},];

    const rowSelection = {};
    const pagination = {defaultCurrent:1, pageSize: this.state.pageSize,
                      current:this.state.currentPage, total: customers.totalElements,
                      showSizeChanger:{},};

    const content = (
      <div style={{marginTop:'10px',background: '#fff'}}>
          <Button style={{margin: '8px 8px 8px 8px'}} type='primary'>添加客户</Button>
          <Table style={{textAlign:'center'}} size={'small'} rowKey={(record) => record.customerId}
                 columns={columns} dataSource={customers.data}
                 rowSelection={rowSelection} bordered
                 pagination={pagination}
                 onChange={this.handlerOnChange.bind(this)}
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
