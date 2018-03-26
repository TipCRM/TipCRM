/**
 * Created by mosesc on 03/23/18.
 */
import React from 'react';
import {Spin} from 'antd';
import styles from './Index.less';

export default class CommonSpin extends React.Component{
  render(){
    const {spinning, children} = this.props;
    return(<Spin spinning={spinning} tip="加载中..." className={styles.commonSpin}>{children}</Spin>);
  }
}
