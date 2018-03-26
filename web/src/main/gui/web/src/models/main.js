/**
 * Created by mosesc on 03/21/18.
 */
import { fakeGetMenu } from '../services/api';
import {message} from 'antd';

export default {
  namespace: 'main',
  state: {
    menus:[]
  },
  effects: {
    *getMenus(_, {call, put}){
      console.log("start init menu");
      const response = yield call(fakeGetMenu);
      yield put({
        type: 'saveMenus',
        payload: response,
      });
    }
  },
  reducers: {
    saveMenus(state, {payload}){
      return{
        ...state,
        menus: payload,
      }
    }
  }
}
