/**
 * Created by mosesc on 03/21/18.
 */
import React from 'react';
import { connect } from 'dva';
import CardCell from '../../components/CardCell/CardCell';
import CommonTab from '../../components/Common/CommonTab';
import CustomerPanel from '../../components/Panel/Customer/CustomerPanel';
import CommonSpin from '../../components/Common/CommonSpin';
import NotificationPanel from '../../components/Panel/Notification/NotificationPanel';
import styles from './Index.less';
import {Layout, List, Avatar, Card, Row, Divider} from 'antd';
const {Content, Footer, Header} = Layout;
const {Meta} = Card;

@connect(({main, user, loading}) => ({
  main,
  currentUser: user.currentUser,
  loading:loading.models.main,
  userInfoLoading: loading.models.user,
}))
export default class MainMenu extends React.Component{
  componentWillMount(){
    const {dispatch} = this.props;
    dispatch({
      type: 'user/getCurrentUser',
    });
    dispatch({
      type: 'main/getMenus',
    });
  }
  render(){
    const {main, loading, currentUser, userInfoLoading } = this.props;
    console.log(currentUser);
    const menus = main.menus;
    const finalMenus = menus ? menus.map(item => {
      if (item.title === '客户管理'){
        return {...item, content:(<CustomerPanel children={item.children}/>)}
      } else if (item.title === '通知中心'){
        return {...item, content:(<NotificationPanel children={item.children}/>)}
      }
      return item;
      }
    ) : menus;
    return(
      <Layout className={styles.layout}>
        <Content className={styles.content}>
          <CommonSpin spinning={userInfoLoading}>
            <div >
              <div>
                <Avatar size="large" style={{width:'80px', height:'80px'}} src="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png"/>
              </div>
              <Row> 用户名： {currentUser ? currentUser.userName : '加载中...'} </Row>
              <Row> 部门： {currentUser ? currentUser.department : '加载中...'} </Row>
              <Row> 职位： {currentUser ? currentUser.level : '加载中...'} </Row>
              <Row> 入职时间： {currentUser ? currentUser.hireTime : '加载中...'} </Row>
            </div>
          </CommonSpin>
          <Divider></Divider>
          <CommonSpin spinning={loading}>
            <List dataSource={finalMenus} style={{ marginTop:'20px'}}
                  grid={{ gutter: 20, column: 4 }}
                  renderItem={item=>(<List.Item>
                  <CardCell menu={item.title} content={item.content}/>
                  </List.Item>)}
            />
          </CommonSpin>
        </Content>
      </Layout>
    );
  }
}
