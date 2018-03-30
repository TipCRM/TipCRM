/**
 * Created by mosesc on 03/29/18.
 */
import {listRoles, createNewRole, updateRow} from '../services/api';
import {message} from 'antd';

export default {
  namespace: 'role',
  state: {
    roles:[],
    selectRole:{},
    createNew: false,
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
        message.success("创建角色成功");
        yield put({
          type:'saveSelectRole',
          payload: {selectRole: {id: response.data, ...payload}, createNew: false},
        });
        yield put({
          type: 'permission/listRolePermission',
          payload: {id: response.data}
        });
        yield put({
          type: 'fetchRoles',
        });
      }
    },
    *changeRole({payload}, {call, put}){
      var {selectRole} = payload;
      const request = {id: selectRole.id, name: payload.newName}
      const response = yield call(updateRow, request);
      if (response.status === 200){
        message.success("修改角色信息成功");
        selectRole.name = payload.newName;
        yield put({
          type:'saveSelectRole',
          payload: {selectRole: selectRole, createNew: false},
        });
        yield put({
          type: 'permission/listRolePermission',
          payload: {id: selectRole.id}
        });
        yield put({
          type: 'fetchRoles',
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
      console.log(payload);
      return{
        ...state,
        selectRole: payload.selectRole,
        createNew: payload.createNew,
      }
    }
  }
}
