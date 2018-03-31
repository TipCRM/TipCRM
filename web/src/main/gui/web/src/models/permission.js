/**
 * Created by mosesc on 03/29/18.
 */
import {listRolePermissions, changeRolePermissions, getPermissionsByMenu} from '../services/api';
import {message} from 'antd';

export default {
  namespace: 'permission',
  state: {
    permissions:[],
    menuPermissions: {
      menuId:{},
      permissions:[],
    }
  },
  effects:{
    *listRolePermission({payload}, {call, put}){
      const response = yield call(listRolePermissions, payload);
      yield put({
        type: 'saveRolePermissions',
        payload: response.data,
      });
    },
    *changeRolePermissions({payload}, {call}){
      const response = yield call(changeRolePermissions, payload);
      if (response.status === 200){
        message.success("成功修改权限");
      }
    },
    *getPermissionsByMenu({payload}, {call, put}){
      const response = yield call(getPermissionsByMenu, payload);
      const menuPermissions = {...payload, permissions: response.data};
      yield put({
        type: 'saveMenuPermissions',
        payload: menuPermissions,
      });
    }
  },
  reducers:{
    saveRolePermissions(state, {payload}){
      return{
        ...state,
        permissions: payload,
      };
    },
    saveMenuPermissions(state, {payload}){
      return{
        ...state,
        menuPermissions: payload,
      };
    }
  }
}
