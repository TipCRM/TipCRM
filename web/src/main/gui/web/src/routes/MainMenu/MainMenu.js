/**
 * Created by mosesc on 03/21/18.
 */
import React from 'react';
import { connect } from 'dva';
import CardCell from '../../components/CardCell/CardCell';
import CommonTab from '../../components/Common/CommonTab';
import CommonSpin from '../../components/Common/CommonSpin';
import NotificationPanel from '../../components/Panel/Notification/NotificationPanel';
import styles from './Index.less';
import {menuComponentConstant} from '../../utils/Constant';
import {Layout, List, Avatar, Card, Row, Divider, Button} from 'antd';
const {Content, Footer, Header} = Layout;
const {Meta} = Card;

@connect(({main, user, loading}) => ({
  main,
  currentUser: user.currentUser,
  loading:loading.models.main,
  userInfoLoading: loading.effects['user/getCurrentUser'],
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
  handleLogout(){
    const {dispatch} = this.props;
    dispatch({
      type: 'login/logout',
    });
  }

  render(){
    const {main, loading, currentUser, userInfoLoading } = this.props;
    console.log(currentUser);
    const menus = main.menus;
    const finalMenus = menus ? menus.map(item => {
        return {...item, content: item.active ? menuComponentConstant(item) : item.content};
      }
    ) : menus;
    return(
      <Layout className={styles.layout}>
        <Header style={{height: '40px'}}>
          <div style={{textAlign: 'right', marginRight: '20%'}}>
            <div style={{color:'#0000'}}>{currentUser ? currentUser.userName : '加载中...'}</div>
          </div>
          <Button onClick={this.handleLogout.bind(this)}>退出登录</Button>
        </Header>
        <Content className={styles.content}>
          <CommonSpin spinning={loading}>
            <List dataSource={finalMenus}
                  grid={{ gutter: 20, column: 8 }}
                  renderItem={item=>(<List.Item style={{marginTop:"20px"}}>
                  <CardCell title={item.title} content={item.content} active={item.active}/>
                  </List.Item>)}
            />
          </CommonSpin>
        </Content>
      </Layout>
    );
  }
}
