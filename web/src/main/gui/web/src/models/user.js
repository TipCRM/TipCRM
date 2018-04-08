import { userLogin, fetchCurrentUser,
  fetchUserByName, createNewUser,
  fetchUserDetailInfo, disMissUser,
  updateUserInfo} from '../services/api';
import {message} from 'antd';
import {routerRedux} from 'dva/router';

export default {
  namespace: 'user',
  state: {
    currentUser: {},
    selectUser: {},
    users:[],
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
      const response = yield call(fetchCurrentUser);
      yield put({
        type: 'saveCurrentUser',
        payload: response,
      });
    },
    *getUserByName({payload}, {call, put}){
      const response = yield call(fetchUserByName, payload);
      yield put({
        type: 'saveUsers',
        payload: response.data,
      });
    },
    *createNewUser({payload}, {call, put}){
      const response = yield call(createNewUser, payload);
      if (response.status === 200){
        message.success('创建员工成功');
        yield put()
      }
    },
    *getUserDetailInfo({payload}, {call, put}){
      const response = yield call(fetchUserDetailInfo, payload);
      yield put({
        type: 'saveSelectUser',
        payload: response.data,
      });
    },
    *disMissUser({payload}, {call, put}){
      const response = yield call(disMissUser, payload);
      if (response.status === 200){
        message.success('离职员工成功');
      }
    },
    *updateUser({payload}, {call, put}){
      const response = yield call(updateUserInfo, payload);
      if (response.status === 200){
        message.success('修改员工信息成功');
      }
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
    },
    saveUsers(state, {payload}){
      return{
        ...state,
        users: payload,
      }
    },
    saveSelectUser(state, {payload}){
      return{
        ...state,
        selectUser: payload,
      }
    }
  },
};
