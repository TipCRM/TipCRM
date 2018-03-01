/**
 * Created by mosesc on 02/02/18.
 */
import React from 'react';
import {connect} from 'dva';
import {Spin, Icon, Table, Button, Popover, List, Input, Tooltip, Modal, Form, Row, message} from 'antd';
import Loader from '../../components/Loader/Loader';
import styles from '../Customer/Customer.less';
import EditableItem from '../../components/EditableItem';

const FormItem = Form.Item;
/**
 * @author brotherlu
 * @date 2018-2-3
 */
@connect(({customer, loading})=>
  ({customer,
    loading: loading.models.customer}))
@Form.create()
export default class Customer extends React.PureComponent{

  state={
    filterDropdownVisible: false,
    searchText:'',
    filtered: false,
    currentPage: 1,
    pageSize: 10,
    filterCondition:{},
    sorterCondition:{sort:{direction:'DESC', fieldName:'customer_name'}},
    showModal: false,
  }

  componentDidMount(){
    const {dispatch} = this.props;
    let pageCondition = {page: this.state.currentPage, size: this.state.pageSize};
    let request = {...pageCondition, ...this.state.filterCondition, ...this.state.sorterCondition};
    dispatch({
      type:'customer/myCustomers',
      payload:request,
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

  /**
   *  when the table change, include: pagination/filter/sorter.
   *  the method will use, but not include the filter defined by
   *  user.
   */
  handlerOnChange=(pagination, filters, sorter) => {
    console.log(">>>>>>filter: "+JSON.stringify(filters));
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

  /**
   * add customer
   */
  createCustomer = () =>{
    this.setState({
      showModal: true,
    });
  }

  /**
   * give up create customer
   */
  cancelCreateCustomer =()=>{
    this.setState({
      showModal: false,
    });
  }

  /**
   * sure create customer
   */
  sureCreateCustomer=(e)=>{
    e.preventDefault();
    this.props.form.validateFieldsAndScroll((errors, values) => {
      if (errors) {
        return;
      }
      this.props.dispatch(
        { type: 'customer/createCustomer',
          payload: values
        });
      this.setState({
        showModal: false,
      });
      message.success("创建客户信息成功，已提交审核");
    });
  }

  render(){
    const {loading, customer, form} = this.props;
    const {getFieldDecorator} = form;
    const customers = customer.customers;
    /** customer name dropdown **/
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
    /** create customer info form **/
    const modalTitle = (<div>
      <font>添加新客户</font>
      <Tooltip placement="rightBottom" title="新添加的用户需要上级审核通过后才会录入客户数据库" mouseLeaveDelay="0.3">
        <Icon type="info-circle-o" style={{color:'#faad14',marginLeft:'3px'}} size="small"/>
      </Tooltip>
    </div>);
    const newCustomerModal = (
      <Modal visible={this.state.showModal}
             title={modalTitle} width="330px"
             onCancel={this.cancelCreateCustomer} footer={null}>
        <Form>
          <FormItem hasFeedback>
            {getFieldDecorator('name', {
              rules: [
                {
                  required: true,
                  message:'客户名称不能为空'
                },
              ],
            })(<Input onPressEnter={this.sureCreateCustomer} placeholder="客户名称" prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}/>)}
          </FormItem>
          <FormItem hasFeedback>
            {getFieldDecorator('contactName', {
              rules: [
                {
                  required: true,
                  message:'联系人名字不能为空'
                },
              ],
            })(<Input onPressEnter={this.sureCreateCustomer} placeholder="联系人名字" prefix={<Icon type="contacts" style={{ color: 'rgba(0,0,0,.25)' }} />}/>)}
          </FormItem>
          <FormItem hasFeedback>
            {getFieldDecorator('contactPhone', {
              rules: [
                {
                  required: true,
                  message: '请输入手机号！',
                },
                {
                  pattern: /^1\d{10}$/,
                  message: '手机号格式错误！',
                },
              ],
            })(
              <Input
                placeholder="联系人11位手机号"
                prefix={<Icon type="mobile" style={{ color: 'rgba(0,0,0,.25)' }}/>}
              />
            )}
          </FormItem>
          <FormItem hasFeedback>
            {getFieldDecorator('address', {
              rules: [
                {
                  required: true,
                  message:'客户地址不能为空'
                },
              ],
            })(<Input onPressEnter={this.sureCreateCustomer} placeholder="客户地址" prefix={<Icon type="home" style={{ color: 'rgba(0,0,0,.25)' }} />}/>)}
          </FormItem>
          <FormItem>
            <Row>
              <Button key="back" onClick={this.cancelCreateCustomer} style={{width:'48%'}}>取消</Button>
              <Button key="submit" type="primary" loading={loading} onClick={this.sureCreateCustomer} style={{width:'48%', marginLeft:'4%'}}>
                保存
              </Button>
            </Row>
          </FormItem>
        </Form>
      </Modal>
    );

    const columns=[
      {title:'公司名称',dataIndex:'customerName',key:'customerId',width: '10%',
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
          <EditableItem value={text} showSaveIcon={true}/>
        );
      }),},
      {title:'联系电话',dataIndex:'contactPhone',width: '11%',
        render:((text) =>{
          return (
            <EditableItem value={text} type="contactPhone" showSaveIcon={true}/>
          );
      }),},
      {title:'最后一次沟通时间',dataIndex:'lastCommunicationTime',width: '14%',},
      {title:'拜访记录',dataIndex:'lastCommunicationContent', width:'15%',
        render:((text, record) =>{
          return(
            <Popover content={this.initPopover(record)} trigger="click" placement="bottom" >
              <div style={{cursor:'pointer'}}>{text}</div>
            </Popover>);}),className:styles.tableColumn},
      {title:'下次沟通',dataIndex:'nextCommunicationTime',width: '14%',},
      {title:'客户状态',dataIndex:'status',width: '10%', filters:[
        {text:'意向客户',value:1},
        {text:'签约客户',value:2},
        {text:'新客户',value:3},
        {text:'过期客户',value:4},], sorter: true},
      {title:'意向金额', dataIndex:'intentionalAmount'},
      {title:'签约金额', dataIndex:'signAmount'},
      {title:'操作', dataIndex:'operation',width: '8%',
        render:((text, record) => {
          return(
            <div>
              <Tooltip placement="top" title={record.customerId}>
                <Button size="small">
                  <Icon type="logout" style={{ fontSize: 16, color: '#08c' }}/>
                </Button>
              </Tooltip>
              <Tooltip placement="top" title={'提交审核'}>
                <Button size="small" style={{ marginLeft:5}}>
                  <Icon type="export" style={{ fontSize: 18, color: '#08c'}}/>
                </Button>
              </Tooltip>
            </div>
          );
        }),
      }
    ];

    const rowSelection = {};
    const pagination = {defaultCurrent:1, pageSize: this.state.pageSize,
                      current:this.state.currentPage, total: customers.totalElements,
                      showSizeChanger:{},};

    const content = (
      <div style={{marginTop:'10px',background: '#fff'}}>
          <Button style={{margin: '8px 8px 8px 8px'}} type='primary' onClick={this.createCustomer}>添加客户</Button>
          {newCustomerModal}
          <Table style={{textAlign:'center'}} size="small" rowKey={(record) => record.customerId}
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
