/**
 * Created by mosesc on 02/28/18.
 */
import React from 'react';
import {connect} from 'dva';
import {Card, Tooltip, Icon, Spin, Row, Col} from 'antd';
import DescriptionList from '../../components/DescriptionList';

const { Description } = DescriptionList;
@connect(({user,loading})=>({
  currentUser:user.currentUser,
  loading: loading.models.user}))
export default class UserCenter extends React.PureComponent {
  componentDidMount(){
    const {dispatch} = this.props;
    dispatch({
      type:'user/fetchCurrent',
    });
  }
  render(){
    const {loading, currentUser} = this.props;
    const userInfoTitle = (
      <div>
        <Row>
          <Col span={6} >个人信息</Col>
          <Col span={6} offset={12}><a><Icon type="edit" /></a></Col>
        </Row>
      </div>
    );
    const userInfo = (
      <Card title={userInfoTitle} style={{ marginBottom: 24 }} bordered={false}>{
      currentUser ? <DescriptionList style={{ marginBottom: 24 }}>
        <Description term="用户姓名">{currentUser.userName}</Description>
        <Description term="联系方式">{currentUser.phoneNo}</Description>
        <Description term="用户邮箱">{currentUser.email}</Description>
        <Description term="所在部门">{currentUser.department}</Description>
        <Description term="职位">{currentUser.level}</Description>
        <Description term="入职时间">{currentUser.hireTime}</Description>
      </DescriptionList> : <Spin size="small" spinning={loading}/>
    }</Card>);

    const userNotice = (
      <Card title="个人通知" style={{ marginBottom: 24 }} bordered={false}>
      </Card>);
    return(
      <div>
        {userInfo}
        {userNotice}
      </div>
    );
  };
}
