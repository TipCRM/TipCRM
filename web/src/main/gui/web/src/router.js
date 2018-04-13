import React from 'react';
import { Router, Route, Switch, Redirect } from 'dva/router';
import Login from './routes/User/Login';
import MainMenu from './routes/MainMenu/MainMenu';
import { LocaleProvider } from 'antd';
import zh_CN from 'antd/lib/locale-provider/zh_CN';
import 'moment/locale/zh-cn';

function RouterConfig({ history }) {
  return (
  <LocaleProvider locale={zh_CN}>
    <Router history={history}>
      <Switch>
        <Route path="/login" exact component={Login} />
        <Route path="/index" exact component={MainMenu} />
        <Redirect exact from="/" to="/login"/>
      </Switch>
    </Router>
  </LocaleProvider>
  );
}

export default RouterConfig;
