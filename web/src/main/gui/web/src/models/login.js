/**
 * Created by mosesc on 04/13/18.
 */
import { userLogin, userLogout} from '../services/api';
import {message} from 'antd';
import {routerRedux} from 'dva/router';
import {setAuthority} from '../utils/authority';

export default{
  namespace: 'login',
  state:{},
  effects: {
    *login({ payload }, { call, put }) {
      const response = yield call(userLogin, payload);
      // Login successfully
      if (response.status === 200) {
        yield put(routerRedux.push('/index'));
        setAuthority(true);
      } else {
        message.error(response.message);
        setAuthority(false);
      }
    },
    *logout(_, { call, put }) {
      yield call(userLogout);
      yield put(routerRedux.push('/login'));
      setAuthority(false);
    },
  }
};
