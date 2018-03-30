/**
 * Created by mosesc on 03/03/18.
 */
import {flushRoleCache} from '../services/api';

export default {
  namespace: 'config',
  state: [],
  effects:{
    *customerStatus(_,{call, put}){
      const response = yield call();
      yield put({
        type: 'saveStatus',
        payload: response.data,
      });
    },
    *flushRoleAndPermissionCache(_, {call}){
      yield call(flushRoleCache);
    }
  },
  reducers:{
    saveStatus(state, {payload}){
        return{
          ...state,
          status: payload,
        };
    }
  }
}
