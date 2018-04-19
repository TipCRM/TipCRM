import React from 'react';
import { Router, Route, Switch, Redirect } from 'dva/router';
import Login from './routes/User/Login';
import MainMenu from './routes/MainMenu/MainMenu';
import { LocaleProvider } from 'antd';
import zh_CN from 'antd/lib/locale-provider/zh_CN';
import 'moment/locale/zh-cn';

function isLogin(nextState, replaceState){
  if (!localStorage.getItem("tip_user_login")){
    replaceState("/login");
  } else{
    replaceState("/index");
  }
}
function RouterConfig({ history }) {
  return (
  <LocaleProvider locale={zh_CN}>
    <Router history={history}>
      <Switch>
        <Route path="/login" exact component={Login} onEnter={isLogin}/>
        <Route path="/index" exact component={MainMenu} onEnter={isLogin}/>
        <Redirect exact from="/" to="/login"/>
      </Switch>
    </Router>
  </LocaleProvider>
  );
}

export default RouterConfig;
