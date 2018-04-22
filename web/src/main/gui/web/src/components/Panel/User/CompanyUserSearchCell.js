/**
 * Created by mosesc on 04/12/18.
 */
import React from 'react';
import {Input, Button, Icon, Form, Select, Radio} from 'antd';
import TipCheckableTag from '../../Common/TipCheckableTag';
import CommonTagGroup from '../../Common/CommonTagGroup';
import styles from './Index.less';
const {Search} = Input;
const FormItem = Form.Item;
const {Option} = Select;
const RadioGroup = Radio.Group;
const RadioButton = Radio.Button;

export default class DepartmentUserSearchCell extends React.Component{
  render(){
    const {searchByName, enableAdd, createNewUser, tags, selectDepartments,
      onChangeSearchStatus, advanceSearch, onDepartmentChange,
      departments, onFilterChange, onClearAdvanceCondition, onAdvanceSearch,
      checkedAlive, checkedDead, onCheckedAlive, onCheckedDead, filterValue} = this.props;
    // common search
    const commonSearchCell = (<div>
      <div  style={{textAlign: 'center', marginBottom: '8px', marginTop:'16px'}}>
        <Search enterButton onSearch={searchByName} className={styles.searchByName} placeholder="输入员工名"/>
      </div>
      <div style={{textAlign: 'center'}}>
        <a onClick={onChangeSearchStatus}>
      <Icon type="double-right" className={styles['advance-search']}/> <font style={{size: '11.5px'}}> 高级搜索</font>
    </a>
    </div>
      <div  className={styles.searchByStatus}>
        <CommonTagGroup tags={tags}/>
        {
          enableAdd ? <Button type="primary" icon="plus" style={{marginLeft: '16px'}} onClick={createNewUser}>添加员工</Button> :''
        }
      </div>
    </div>);

    const formItemLayout = {
      labelCol: {
        xs: { span: 4 },
        sm: { span: 4 },
      },
      wrapperCol: {
        xs: { span: 16 },
        sm: { span: 16 },
      },
    };
    const tailFormItemLayout = {
      wrapperCol: {
        xs: {
          span: 8,
          offset: -8,
        },
        sm: {
          span: 8,
          offset: 8,
        },
      },
    };
    const advanceSearchCell = (<div className={styles['advance-search-cell']}>
      <Form style={{marginLeft:'10%', marginTop: '24px'}} onSubmit={onAdvanceSearch}>
        <FormItem label="员工状态" {...formItemLayout}>
          <TipCheckableTag tagChecked={checkedAlive} handleTagChange={onCheckedAlive}>在职员工</TipCheckableTag>
          <TipCheckableTag tagChecked={checkedDead} handleTagChange={onCheckedDead}>离职员工</TipCheckableTag>
        </FormItem >
        <FormItem label="所在部门" {...formItemLayout} style={{marginTop:'-20px'}}>
          <Select size = "small"  value={selectDepartments}
            mode="tags" placeholder="选择部门"
            onChange={onDepartmentChange} style={{ width: '80%' }}>
            {
              departments.map(department => <Option key={department.id+""}>{department.name}</Option>)
            }
          </Select>
        </FormItem>
        <FormItem label="排序方式" {...formItemLayout} style={{marginTop:'-20px'}}>
          <RadioGroup size="small" onChange={onFilterChange} value={filterValue}>
            <RadioButton value="id">员工编号</RadioButton>
            <RadioButton value="user_name">员工姓名</RadioButton>
            <RadioButton value="status">员工状态</RadioButton>
            <RadioButton value="level">员工等级</RadioButton>
          </RadioGroup>
        </FormItem >
        <FormItem {...tailFormItemLayout} style={{marginTop:'-18px'}}>
          <Button type="primary" htmlType="submit" icon="search" size="small">开始搜索</Button>
          <Button size="small" style={{marginLeft:'8px'}} onClick={onClearAdvanceCondition}>清除条件</Button>
        </FormItem>
      </Form>
      <div style={{textAlign:'center', marginTop:'-18px'}}>
        <a onClick={onChangeSearchStatus}>
          <Icon type="double-left" className={styles['advance-search']}/> <font style={{size: '10px'}}>收起</font>
        </a>
      </div>
    </div>);

    return(<div>{advanceSearch ? advanceSearchCell : commonSearchCell}</div>)
  }
}
