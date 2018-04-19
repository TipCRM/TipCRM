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
import { Chart, Geom, Axis, Tooltip, Legend, Coord } from 'bizcharts';
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

    const data = [
      { genre: 'Sports', sold: 275, income: 2300 },
      { genre: 'Strategy', sold: 115, income: 667 },
      { genre: 'Action', sold: 120, income: 982 },
      { genre: 'Shooter', sold: 350, income: 5271 },
      { genre: 'Other', sold: 150, income: 3710 }
    ];
    // 定义度量
    const cols = {
      sold: { alias: '销售量' },
      genre: { alias: '游戏种类' }
    };

    const footer =<Footer>
      <div>
        <Chart width={300} height={200} data={data} scale={cols}>
          <Axis name="genre" />
          <Axis name="sold" />
          <Legend position="top" dy={-20} />
          <Tooltip />
          <Geom type="interval" position="genre*sold" color="genre" />
        </Chart>
      </div>
    </Footer>;
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
              {
                finalMenus.map(item=>
                  <CardCell title={item.title} content={item.content} active={item.active}/>)
              }
          </CommonSpin>
        </Content>
      </Layout>
    );
  }
}
