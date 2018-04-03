/**
 * Created by mosesc on 04/03/18.
 */
import {fetchAllDepartments, createNewDepartment, updateDepartment} from '../services/api';

export default {
  namespace: 'department',
  state: {
    departments: []
  },
  effects: {
    *listDepartments(_, {call, put}){
      const response = yield call(fetchAllDepartments);
      var departments = response.data.map(item => {return{...item, editing: false}});
      yield put({
        type: 'saveDepartments',
        payload: departments,
      })
    },
    *createNewDepartment({payload}, {call, put}){
      const response = yield call(createNewDepartment, payload);
    },
    *updateDepartment({payload}, {call, put}){

    }
  },
  reducers: {
    saveDepartments(state, {payload}){
      console.log(payload);
      return{
        ...state,
        departments: payload,
      }
    }
  }
};
