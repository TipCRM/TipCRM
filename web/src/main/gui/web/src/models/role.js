/**
 * Created by mosesc on 03/29/18.
 */
import {listRoles} from '../services/api';

export default {
  namespace: 'role',
  state: {
    roles:[],
  },
  effects:{
    *fetchRoles(_,{call, put}){
      const response = yield call(listRoles);
      yield put({
        type: 'saveRoles',
        payload: response.data,
      });
    }
  },
  reducers:{
    saveRoles(state, {payload}){
      return{
        ...state,
        roles: payload
      }
    }
  }
}
