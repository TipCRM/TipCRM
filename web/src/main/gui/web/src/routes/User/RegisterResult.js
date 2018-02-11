import React from 'react';
import { Button } from 'antd';
import { Link } from 'dva/router';
import Result from '../../components/Result';
import styles from './Index.less';
import {fakeAccountLogin} from '../../services/api'

const title = <div className={styles.title}>你的账户：AntDesign@example.com 注册成功</div>;

const handlerOnClick=(e)=>{
  e.preventDefault();
  const res = fakeAccountLogin({loginKey:'AAAA',password:'aaaa'});
  const {data} = res;
  console.log(data);
  console.log(res);
  return res;
}

const actions = (
  <div className={styles.actions}>
    <Link to="/"><Button onClick={handlerOnClick}>返回首页</Button></Link>
  </div>
);

export default () => (
  <Result
    className={styles.registerResult}
    type="success"
    title={title}
    description="激活邮件已发送到你的邮箱中，邮件有效期为24小时。请及时登录邮箱，点击邮件中的链接激活帐户。"
    actions={actions}
    style={{ marginTop: 56 }}
  />
);
