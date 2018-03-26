import { userLogin, fetchCurrentUser } from '../services/api';
import {message} from 'antd';
import {routerRedux} from 'dva/router';

export default {
  namespace: 'user',
  state: {
    currentUser: {},
  },
  effects: {
    *login({ payload }, { call, put }) {
      const response = yield call(userLogin, payload);
      console.log(response);
      // Login successfully
      if (response.status === 200) {
        yield put(routerRedux.push('/index'));
      } else {
        message.error(response.message);
      }
    },
    *logout(_, { put, select }) {
    },
    *getCurrentUser(_,{call, put}){
      const respone = yield call(fetchCurrentUser);
      yield put({
        type: 'saveCurrentUser',
        payload: respone,
      });
    }
  },

  reducers: {
    changeLoginStatus(state, { payload }) {
      return {
        ...state,
        status: payload.status,
        type: payload.type,
        data: payload.data,
      };
    },
    saveCurrentUser(state, {payload}){
      return{
        ...state,
        currentUser: payload.data,
      }
    }
  },
};
