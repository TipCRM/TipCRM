/**
 * Created by mosesc on 04/11/18.
 */
import React from 'react';
import CommonSpin from '../../Common/CommonSpin';
import {connect} from 'dva';
import {Input, Button, Icon, Col, Upload, Form, Modal, Select} from 'antd';
import styles from './Index.less';
const FormItem = Form.Item;
const {TextArea} = Input;
const {Option} = Select;

@connect(({loading, user})=>({
  userLoading: loading.models.user,
  selectUserInfo: user.selectUserInfo
}))
export default class UserDetailInfoPanel extends React.Component{
  state={
    createNew: this.props.createNew,
    selectingDepartment: false,
    selectDepartment: undefined,
  }
  componentDidMount(){
    const {dispatch, selectUser} = this.props;
    if (selectUser.id){
      dispatch({
        type: 'user/getUserDetailInfo',
        payload: {userId: selectUser.id},
      });
      this.setState({
        selectDepartment: this.props.selectUserInfo.departmentId,
      });
    } else{
      dispatch({
        type: 'user/saveSelectUser',
        payload: {}
      });
    }
  }

  handleSaveUser(selectUserInfo){
    const {dispatch} = this.props;
    if (selectUserInfo.id){
      dispatch({
        type: 'user/updateUser',
        payload: {}
      });
    } else{
      dispatch({
        type: 'user/createNewUser',
        payload: {}
      });
    }
    this.setState({
      createNew: false,
    });
  }

  handleDepartmentChange(value){
    this.setState({
      selectDepartment: value,
    });
  }
  handleSaveOrEditDepartment(selectUserInfo){
    const {selectingDepartment, selectDepartment} = this.state;
    const {departments} = this.props;
    if (selectingDepartment){
      const {dispatch} = this.props;
      const selectDepartmentName = departments.filter(department => department.id == selectDepartment)[0].name;
      dispatch({
        type: 'user/changeUserDepartment',
        payload: {
          departmentId: selectDepartment,
          departmentName: selectDepartmentName,
          userId: selectUserInfo.id
        }
      });
    }
    this.setState({
      selectingDepartment: !selectingDepartment,
    });
  }
  handleCancelSaveDepartment(){
    this.setState({
      selectingDepartment: !this.state.selectingDepartment,
      selectDepartment: undefined,
    });
  }

  render(){
    const {selectUserInfo, userLoading, handleCancelSave, enableEdit, departments} = this.props;
    const {createNew, selectingDepartment, selectDepartment} =  this.state;
    const formItemLayout = {
      labelCol: {
        xs: { span: 8 },
        sm: { span: 8 },
      },
      wrapperCol: {
        xs: { span: 12 },
        sm: { span: 12 },
      },
    };
    const uploadButton = (
      <div>
        <Icon type={this.state.loading ? 'loading' : 'plus'} />
        <div className="ant-upload-text">Upload</div>
      </div>
    );
    return(<CommonSpin spinning={userLoading}>
      <div>
        <div style={{float: 'left', width: '30%', marginTop: '10%'}}>
          <div>
            <Upload
              name="avatar"
              listType="picture-card"
              disabled={!createNew}
              action="/avatar">
              {selectUserInfo.avatar ? <img src={"http://www.potafish.com/avatar/"+selectUserInfo.avatar} style={{height:'100px', width:'100px'}}/> : uploadButton}
            </Upload>
          </div>
          <div>
            <b>座右铭：</b>
            <TextArea row="4" value={selectUserInfo.motto} style={{marginTop: '4px'}} disabled={!createNew}/>
          </div>
        </div>
        <div style={{float: 'left', width:'70%'}}>
          <Form style={{marginTop: '16px'}}>
            {
              createNew ? "" : <FormItem label="员工编号" {...formItemLayout} style={{marginTop:'-24px'}}>{selectUserInfo.workNo}</FormItem>
            }
            <FormItem label="用户名" {...formItemLayout} style={{marginTop:'-24px'}}>
              {createNew ? <Input value={selectUserInfo.name} size="small"/> : selectUserInfo.name}
            </FormItem>
            <FormItem label="邮箱" {...formItemLayout} style={{marginTop:'-24px'}}>
              {createNew ? <Input value={selectUserInfo.email} size="small"/> : selectUserInfo.email}
            </FormItem>
            <FormItem label="联系电话" {...formItemLayout} style={{marginTop:'-24px'}}>
              {createNew ? <Input value={selectUserInfo.phoneNo} size="small"/> : selectUserInfo.phoneNo}</FormItem>
            <FormItem label="身份证" {...formItemLayout} style={{marginTop:'-24px'}}>
              {createNew ? <Input value={selectUserInfo.idCard} size="small"/> : selectUserInfo.idCard}
            </FormItem>
            <FormItem label="所在部门" {...formItemLayout} style={{marginTop:'-24px'}}>
              {
                selectingDepartment ? <Select value={selectDepartment} placeholder="选择部门"
                                              onChange={this.handleDepartmentChange.bind(this)} style={{ width: '60%' }}>
                  {
                    departments.map(department => <Option key={department.id+""}>{department.name}</Option>)
                  }
                </Select> : selectUserInfo.department
              }
              {enableEdit ? <a style={{marginLeft:'8px'}} onClick={this.handleSaveOrEditDepartment.bind(this, selectUserInfo)}>
                <Icon type={selectingDepartment ? 'save':'edit'}/></a> : ''}
              {
                selectingDepartment ? <a style={{marginLeft:'8px'}} onClick={this.handleCancelSaveDepartment.bind(this)}><Icon type="close"/></a> : ''
              }
            </FormItem>
            <FormItem label="员工等级" {...formItemLayout} style={{marginTop:'-24px'}}>{selectUserInfo.level}</FormItem>
            <FormItem label="提成比例" {...formItemLayout} style={{marginTop:'-24px'}}>{createNew ? <Input value={selectUserInfo.idCard} size="small"/> : selectUserInfo.hireTime}</FormItem>
            {
              createNew ? '':<FormItem label="入职时间" {...formItemLayout} style={{marginTop:'-24px'}}>{selectUserInfo.hireTime}</FormItem>
            }
            {
              createNew ? '': <FormItem label="经办人" {...formItemLayout} style={{marginTop:'-24px'}}>{selectUserInfo.hirer}</FormItem>
            }
            {
              createNew ? '': <FormItem label="状态" {...formItemLayout} style={{marginTop:'-24px'}}>{selectUserInfo.status}</FormItem>
            }
          </Form>
        </div>
      </div>
      <div style={{textAlign: 'center',clear: 'both'}}>
        {
          createNew ? <div>
            <Button onClick={handleCancelSave} size="small">取消</Button>
            <Button type="primary" style={{marginLeft: '8px', marginRight:'8px'}} icon="save" size="small" onClick={this.handleSaveUser.bind(this, selectUserInfo)}>保存</Button>
          </div> : ''
        }
      </div>
    </CommonSpin>);
  }
}
