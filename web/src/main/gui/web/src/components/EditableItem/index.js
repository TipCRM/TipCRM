import React, { PureComponent } from 'react';
import { Input, Icon, message } from 'antd';
import styles from './index.less';

export default class EditableItem extends PureComponent {
  state = {
    value: this.props.value,
    editable: this.props.editing ? this.props.editing : false,
  };
  handleChange = (e) => {
    const { value } = e.target;
    this.setState({ value });
  }
  check = () => {
    const {type} = this.props;
    if (type === 'phone'){
      let reg = /^1\d{10}$/;
      if (this.state.value.match(reg) == null){
        message.error('电话号码格式不正确');
        return;
      }
    }
    if (this.state.value == null || this.state.value == ''){
      message.error('内容不能为空');
      return;
    }
    this.setState({ editable: false });
    if (this.props.onChange) {
      this.props.onChange(this.state.value);
    }
  }
  edit = () => {
    this.setState({ editable: true });
  }
  render() {
    const {editing, showSaveIcon} = this.props;
    const { value, editable } = this.state;
    const edit = (editing == null ? editable: editing);
    return (
      <div className={styles.editableItem}>
        {
          edit ? (
            <div className={styles.wrapper}>
              <Input
                size="small"
                value={value}
                onChange={this.handleChange}
                onPressEnter={this.check}
              />
              {
                showSaveIcon ? <Icon
                    type="save"
                    className={styles.icon}
                    onClick={this.check}
                  /> : ""
              }
            </div>
          ) : (
            <div className={styles.wrapper}>
              <span style={{cursor: 'pointer'}} onDoubleClick={this.edit}>{value||'--'}</span>
            </div>
          )
        }
      </div>
    );
  }
}
