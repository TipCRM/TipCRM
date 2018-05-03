/**
 * Created by mosesc on 04/26/18.
 */
import {message} from 'antd';
import {sendNotification} from '../services/api'

export default{
  namespace: 'util',
  state:{
    successGetValidationCode: true,
  },
  effects:{
    *getValidationCode({payload}, {call, put}){
      const response = yield call(sendNotification, payload);
      if (response.status === 200){
        message.success("验证码已发送");
        yield put({
          type: 'saveGetValidationCodeStatus',
          payload: false,
        })
      }
    }
  },
  reducers:{
    saveGetValidationCodeStatus(state, {payload}){
      return{
        ...state,
        successGetValidationCode: payload,
      }
    }
  }
}
