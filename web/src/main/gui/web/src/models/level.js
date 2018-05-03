/**
 * Created by mosesc on 04/27/18.
 */
import {listLevels} from '../services/api'

export default{
  namespace: 'level',
  state: {
    levels: []
  },
  effects: {
    *listLevels(_, {call, put}){
      const response = yield call(listLevels);
      yield put({
        type: 'saveLevels',
        payload: response.data,
      });
    }
  },
  reducers: {
    saveLevels(state, {payload}){
      return{
        ...state,
        levels: payload,
      }
    }
  }
}
