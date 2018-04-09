/**
 * Created by mosesc on 04/03/18.
 */
import {fetchAllDepartments, createNewDepartment, updateDepartment, deletDepartment} from '../services/api';
import {message} from 'antd';

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
      if (response.status === 200){
        message.success('创建部门成功');
        yield put({
          type: 'listDepartments',
        });
      }
    },
    *updateDepartment({payload}, {call, put}){
      const response = yield call(updateDepartment, payload)
      if (response.status === 200){
        message.success('修改部门信息成功');
        yield put({
          type: 'listDepartments',
        });
      }
    },
    *deleteDepartment({payload}, {call, put}){
      const response = yield call(deletDepartment, payload);
      if (response.status === 200){
        message.success('成功删除部门');
        let departments = payload.departments.filter(item => item.id != payload.deleteId);
        yield put({
          type: 'saveDepartments',
          payload: departments,
        })
      }
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
