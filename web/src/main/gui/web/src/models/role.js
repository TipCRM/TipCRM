/**
 * Created by mosesc on 03/29/18.
 */
import {listRoles, createNewRole} from '../services/api';

export default {
  namespace: 'role',
  state: {
    roles:[],
    selectRole:{}
  },
  effects:{
    *fetchRoles(_,{call, put}){
      const response = yield call(listRoles);
      yield put({
        type: 'saveRoles',
        payload: response.data,
      });
    },
    *createNewRole({payload}, {call, put}){
      const response = yield call(createNewRole, payload);
      if (response.status === 200){
        yield put({
          type:'saveSelectRole',
          payload: {id: response.data, ...payload},
        });
        yield put({
          type: 'permission/listRolePermission',
          payload: {id: response.data}
        });
      }
    }
  },
  reducers:{
    saveRoles(state, {payload}){
      return{
        ...state,
        roles: payload
      }
    },
    saveSelectRole(state, {payload}){
      return{
        ...state,
        selectRole: payload,
      }
    }
  }
}
