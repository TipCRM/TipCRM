/**
 * Created by mosesc on 03/22/18.
 */
import React from 'react';
import {Input, Button, Icon} from 'antd';
import styles from './Index.less';
import CommonTagGroup from '../../Common/CommonTagGroup';
const {Search} = Input;

export default class CustomerSearchCell extends React.Component{
  render(){
    const {onQuickSearch, tags} = this.props;
    return(
      <div className={styles.quickSearchPanel}>
        <div>
          <div>
            <Search style={{border:'1px'}} placeholder="输入客户的名字" className={styles.quickSearch} onSearch={onQuickSearch} enterButton/>   <Icon type="plus" />高级搜索
          </div>
        </div>
        <div style={{marginBottom:'8px'}}>
          <CommonTagGroup tags={tags}/>
        </div>
      </div>
    );
  }
}
