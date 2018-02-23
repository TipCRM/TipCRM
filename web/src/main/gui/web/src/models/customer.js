/**
 * Created by Administrator on 2018/2/9.
 */
import {queryMyCustomers} from '../services/api';

export default {
  namespace: 'customer',
  state: {
    customers:[],
    data:[],
  },
  effects: {
    *myCustomers({payload},{call, put}){
      console.log(payload);
      const response = yield call(queryMyCustomers,payload);
      yield put({
        type:'listCustomers',
        payload: response.data,
      });
    },
    *popoverData({payload},{call, put}){
      console.log('id'+payload);
      let response =[];
      if (payload == 1){
        response = {...response,...{desc:'1997-12-11 拜访麻花藤'}};
      } else if (payload == 2){
        response = {...response,...{desc:'1993-12-11 拜访Jack Ma', desc:'2003-12-1 再次相会于西湖'}};
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
