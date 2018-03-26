/**
 * Created by Administrator on 2018/2/9.
 */
import {queryMyCustomers, createCustomer, transferOutCustomer} from '../services/api';

export default {
  namespace: 'customer',
  state: {
    customers:[],
    data:[],
  },
  effects: {
    *myCustomers({payload},{call, put}){
      const response = yield call(queryMyCustomers,payload);
      yield put({
        type:'listCustomers',
        payload: response.data,
      });
    },
    *createCustomer({payload}, {call, put}){
      console.log(payload);
      const response = yield call(createCustomer, payload);
      console.log(response);
    },
    *transferOut({payload}, {call, put}){
      const response = yield call(transferOutCustomer, payload);
      console.log(response);
    },
    *popoverData({payload},{call, put}){
      console.log('id'+payload);
      let response =[];
      if (payload == 1){
        response = {...response,...{desc:'1997-12-11 拜访麻花藤'}};
      } else if (payload == 2){
        response = {...response,...{desc:'1993-12-11 拜访Jack Ma'}};
      } else {
        response = {...response,...{desc:'1900-12-11 美国留学深造'}};
      }
      //console.log(response);
      yield put({
        type:'listCustomers',
        payload: response,
      });
    },
  },
  reducers: {
    listCustomers(state, action){
      return{
        ...state,
        customers: action.payload,
      };
    },
    listPopoverData(state, action){
      return{
        ...state,
        data: action.payload,
      };
    }
  },
};
