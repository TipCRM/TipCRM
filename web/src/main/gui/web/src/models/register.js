import { fakeRegister } from '../services/api';

export default {
  namespace: 'register',

  state: {
    status: undefined,
  },

  effects: {
    *submit({ payload }, { call, put }) {
      payload = {...payload,...{manager:false}}
      console.log(payload);
      const response = yield call(fakeRegister, payload);
      yield put({
        type: 'registerHandle',
        payload: response,
      });
      if (response.status >= 400){
        throw response.data;
      }
    },
  },

  reducers: {
    registerHandle(state, { payload }) {
      return {
        ...state,
        status: payload.status,
      };
    },
  },
};
