import dva from 'dva';
import './index.css';
import {message} from 'antd';
import createLoading from 'dva-loading';

// 1. Initialize
const app = dva({
  error (error) {
    message.error(error.message)
  },
});

// 2. Plugins
app.use(createLoading());

// 3. Model
app.model(require('./models/user').default);
app.model(require('./models/main').default);
app.model(require('./models/customer').default);
app.model(require('./models/notification').default);

// 4. Router
app.router(require('./router').default);

// 5. Start
app.start('#root');
