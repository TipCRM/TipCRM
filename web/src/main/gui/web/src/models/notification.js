/**
 * Created by mosesc on 03/23/18.
 */
import { fetchMyNotifictions } from '../services/api';

export default {
  namespace: 'notification',
  state: {
    notifications:[]
  },
  effects: {
    *listMyNotification({payload}, {call, put}){
      const response = yield call(fetchMyNotifictions, payload);
      yield put({
        type: 'saveMyNotification',
        payload: response.data,
      });
    }
  },
  reducers: {
    saveMyNotification(state, {payload}){
      return{
        ...state,
        notifications: payload,
      }
    }
  }
}
