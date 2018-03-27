/**
 * Created by mosesc on 03/27/18.
 */
import {notification} from 'antd';

let NotificationUtil = (type, options) => {
  let opt = {
    message: type.replace(/\b(\w)(\w*)/g, ($0, $1, $2) => {
      return $1.toUpperCase() + $2.toLowerCase();
    }),
    className: `snx-notification-${type}`,
  };
  if (options.description
    && options.description.toString().indexOf('<br/>') > -1) {
    options.description = (<div>
      {
        options.description.toString().split('<br/>').map((item) => {
          return (
            <div>{item}</div>
          );
        })
      }
    </div>)
  }
  notification[type](Object.assign({}, options, opt));
};
