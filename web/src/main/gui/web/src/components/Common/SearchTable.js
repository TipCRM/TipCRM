/**
 * Created by mosesc on 03/21/18.
 */
import React from 'react';
import {Table} from 'antd';

export default class SearchTable extends React.Component{
  render(){
    const {tableColumns, tableData, searchContent,
      onTableRow,tablePagination, onTableChange,tableFooter,
      tableRowSelection, tableRowKey, tableClass} = this.props;
    return(
      <div>
        <div>
          {searchContent}
        </div>
        <div className = {tableClass}>
          <Table dataSource={tableData}
                 columns={tableColumns}
                 rowSelection = {tableRowSelection}
                 onRow={onTableRow} size="small"
                 pagination={tablePagination}
                 onChange={onTableChange}
                 footer={tableFooter}
                 rowKey = {tableRowKey}

          />
        </div>
      </div>
    );
  }
}
