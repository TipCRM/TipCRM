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
        <Header style={{height: '40px'}}>
          <div style={{textAlign: 'right', marginRight: '20%'}}>
            <div style={{color:'#eeee'}}>{currentUser ? currentUser.userName : '加载中...'}</div>
          </div>
        </Header>
        <Content className={styles.content}>
          <CommonSpin spinning={userInfoLoading}>
          </CommonSpin>

          <CommonSpin spinning={loading}>
            <List dataSource={finalMenus}
                  grid={{ gutter: 20, column: 5 }}
                  renderItem={item=>(<List.Item style={{marginTop:"20px"}}>
                  <CardCell title={item.title} content={item.content}/>
                  </List.Item>)}
            />
          </CommonSpin>
        </Content>
      </Layout>
    );
  }
}
