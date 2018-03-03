/**
 * Created by mosesc on 03/03/18.
 */
import {queryCustomerStatus} from '../services/api';

export default {
  namespace: 'config',
  state: [],
  effects:{
    *customerStatus(_,{call, put}){
      const response = yield call(queryCustomerStatus);
      console.log(">><SSS"+response);
      yield put({
        type: 'saveStatus',
        payload: response.data,
      });
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
