import { fetchCurrentUser,
  fetchUserByName, createNewUser,
  fetchUserDetailInfo, disMissUser,
  updateUserInfo, fetchCompanyUsers} from '../services/api';
import {message} from 'antd';

export default {
  namespace: 'user',
  state: {
    currentUser: {},
    selectUserInfo: {},
    users:[],
    companyUsers: [],
  },
  effects: {
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
    },
    *listCompanyUsers({payload}, {call, put}){
      console.log("start calling users api: ", payload);
      const response = yield call(fetchCompanyUsers, payload);
      yield put({
        type: 'saveCompanyUsers',
        payload: response.data,
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
        selectUserInfo: payload,
      }
    },
    saveCompanyUsers(state, {payload}){
      return{
        ...state,
        companyUsers: payload,
      }
    }
  },
};
