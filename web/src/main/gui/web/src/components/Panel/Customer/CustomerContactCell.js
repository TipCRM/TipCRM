/**
 * Created by Administrator on 2018/3/22.
 */
import React from 'react';
import {} from 'antd';
import SearchTable from '../../Common/SearchTable';
import CommonSpin from '../../Common/CommonSpin';

export default class CustomerContactCell extends React.Component{
  render(){
    const columns = [{title:'联系人',dataIndex:'contactName',width: '8%'},
      {title:'联系电话',dataIndex:'contactPhone',width: '13%',},
      {title:'职位',dataIndex:'department',width: '8%'},
      {title:'性别',dataIndex:'sex',width: '11%',},
      {title:'录入时间',dataIndex:'entryDate',width: '15%'},
      {title:'记录人员',dataIndex:'entryId',width: '10%'},
      {title:'备注',dataIndex:'comment',width: '15%',},];
    const data = [{contactName: '李华', contactPhone:'183-1205-1568',department:'经理',sex:'先生',entryDate:'2017-12-8',entryId:'黎明',comment:'大佬，多关注'},
      {contactName: '熊猫', contactPhone:'183-1208-1568',department:'总裁',sex:'女生',entryDate:'2017-12-8',entryId:'黎明',comment:'主要看他说的'},];
    return(
      <SearchTable tableColumns={columns} tableData={data}/>
    );
  }
}
