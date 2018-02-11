/**
 * Created by mosesc on 02/02/18.
 */
import React from 'react';
import {connect} from 'dva';
import {Spin, Icon, Table, Button, Popover, List, Input} from 'antd';
import Loader from '../../components/Loader/Loader';
import styles from '../Index/Index.less'

export default class Index extends React.PureComponent{
  render(){
    return(
      <div >
          <Spin size={'default'}  tip="正在加载..." style={{fontSize:14,marginTop:'10%'}} spinning={true}>
            <div style={{marginTop:'10px',textAlign:'center'}}>
              <h1>主页</h1>
            </div>
          </Spin>
      </div>
    );
  }

}
