/**
 * Created by mosesc on 03/29/18.
 */
import {listRolePermissions} from '../services/api';

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
