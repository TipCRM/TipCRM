/**
 * Created by mosesc on 04/12/18.
 */
import React from 'react';
import {Input, Button, Icon, Form, Select, Divider} from 'antd';
import TipCheckableTag from '../../Common/TipCheckableTag';
import CommonTagGroup from '../../Common/CommonTagGroup';
import styles from './Index.less';
const {Search} = Input;
const FormItem = Form.Item;
const {Option} = Select;

export default class DepartmentUserSearchCell extends React.Component{
  render(){
    const {searchByName, enableAdd, createNewUser, tags, onChangeSearchStatus, advanceSearch, statusTags} = this.props;
    const commonSearchCell = (<div>
      <div  style={{textAlign: 'center', marginBottom: '8px', marginTop:'16px'}}>
        <Search enterButton onSearch={searchByName} className={styles.searchByName} placeholder="输入员工名"/>
      </div>
      <div style={{textAlign: 'center'}}>
        <a onClick={onChangeSearchStatus}>
      <Icon type="double-right" className={styles.test}/> <font style={{size: '11.5px'}}> 高级搜索</font>
    </a>
    </div>
      <div  className={styles.searchByStatus}>
        <CommonTagGroup tags={tags}/>
        {
          enableAdd ? <Button type="primary" icon="plus" style={{marginLeft: '16px'}} onClick={createNewUser}>添加员工</Button> :''
        }
      </div>
    </div>);
    const children = [];
    for (let i = 10; i < 36; i++) {
      children.push(<Option key={i.toString(36) + i}>{i.toString(36) + i}</Option>);
    };

    const formItemLayout = {
      labelCol: {
        xs: { span: 8 },
        sm: { span: 8 },
      },
      wrapperCol: {
        xs: { span: 8 },
        sm: { span: 8 },
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
    const advanceSearchCell = (<div style={{marginLeft:'20%', marginRight:'20%',border:'1px dashed #CCCCCC', marginBottom:'16px'}}>
      <Form style={{marginLeft:'10%', marginTop: '24px'}}>
        <FormItem label="员工状态" {...formItemLayout}>
          <TipCheckableTag>在职员工</TipCheckableTag>
          <TipCheckableTag>离职员工</TipCheckableTag>
        </FormItem >
        <FormItem label="所在部门" {...formItemLayout} style={{marginTop:'-20px'}}>
          <Select
            mode="tags"
            placeholder="选择部门"
            style={{ width: '100%' }}
          >
            {children}
          </Select>
        </FormItem>
        <FormItem {...tailFormItemLayout} style={{marginTop:'-18px'}}>
          <Button type="primary" htmlType="submit" icon="search" size="small">开始搜索</Button>
          <Button size="small" style={{marginLeft:'8px'}} onClick={onChangeSearchStatus}>取消</Button>
        </FormItem>
      </Form>
    </div>);
    return(<div>{
      advanceSearch ? advanceSearchCell : commonSearchCell
    }
    </div>)
  }
}
