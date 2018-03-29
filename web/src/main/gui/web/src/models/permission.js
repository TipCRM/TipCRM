/**
 * Created by mosesc on 03/29/18.
 */
import {listRolePermissions, changeRolePermissions} from '../services/api';
import {message} from 'antd';

export default {
  namespace: 'permission',
  state: {
    permissions:[],
  },
  effects:{
    *listRolePermission({payload}, {call, put}){
      const response = yield call(listRolePermissions, payload);
      yield put({
        type: 'saveRolePermissions',
        payload: response.data,
      });
    },
    *changeRolePermissions({payload}, {call}){
      const response = yield call(changeRolePermissions, payload);
      if (response.status === 200){
        message.info("保存更改成功");
      }
    }
  },
  reducers:{
    saveRolePermissions(state, {payload}){
      return{
        ...state,
        permissions: payload,
      };
    }
  }
}
