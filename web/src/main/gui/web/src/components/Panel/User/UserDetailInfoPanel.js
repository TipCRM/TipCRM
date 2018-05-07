/**
 * Created by mosesc on 04/11/18.
 */
import React from 'react';
import CommonSpin from '../../Common/CommonSpin';
import {connect} from 'dva';
import {Input, Button, Icon, Col, Upload, Form, Modal, Select, message} from 'antd';
import styles from './Index.less';
const FormItem = Form.Item;
const {TextArea} = Input;
const {Option} = Select;

@connect(({loading, user})=>({
  userLoading: loading.models.user,
  selectUserInfo: user.selectUserInfo,
}))
export default class UserDetailInfoPanel extends React.Component{
  state={
    createNew: this.props.createNew,
    selectingDepartment: false,
    selectDepartment: undefined,
    selectingLevel: false,
    selectedLevel: undefined,
    paymentPercentage: this.props.selectUserInfo.paymentPercentage,
    dismissingUser: false,
    dismissReason: "",
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

  handleSaveOrEditLevel(selectUserInfo){
    const {selectingLevel, selectedLevel, paymentPercentage} = this.state;
    const {dispatch, levels} = this.props;
    if (selectingLevel){
      //const levelName = levels.filter(level => level.id === selectedLevel)[0].name;
      dispatch({
        type: 'user/changeUserLevel',
        payload: {levelId: selectedLevel, paymentPercentage: paymentPercentage, userId: selectUserInfo.id}
      });
    }
    this.setState({
      selectingLevel: !selectingLevel,
    });
  }
  handleCancelSaveLevel(){
    this.setState({
      selectingLevel: !this.state.selectingLevel,
      selectedLevel: undefined,
    });
  }
  handlePaymentPercentageChange(e){
    try {
      const percentage = parseInt(e.target.value);
      if (percentage> 100 || percentage < 0){
        throw "percentage should between 0 and 100";
      }
      this.setState({
        paymentPercentage: percentage,
      });
    } catch (e){
      message.error("提成比例应该在0-100之间");
    }
  }
  handleLevelChange(value){
    this.setState({
      selectedLevel: value,
    });
  }

  handleOpenOrCloseDismissModal(){
    this.setState({
      dismissingUser: !this.state.dismissingUser,
    });
  }
  handleDismissReasonChange(e){
    const value = e.target.value;
    this.setState({
      dismissReason: value,
    });
  }
  handleDismissUser(selectUserInfo){
    const {dismissReason} = this.state;
    if (dismissReason == null || dismissReason.length === 0){
      message.error("离职理由不能为空！");
      return;
    }
    const {dispatch} = this.props;
    dispatch({
      type: 'user/dismissUser',
      payload: {userId: selectUserInfo.id, reason: dismissReason},
    });
    this.setState({
      dismissingUser: false,
    });
  }


  render(){
    const {selectUserInfo, userLoading, handleCancelSave, enableEdit, departments, levels, enableDelete} = this.props;
    const {createNew, selectingDepartment, selectDepartment, dismissReason,
      selectingLevel, selectedLevel, paymentPercentage, dismissingUser} =  this.state;
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
            <FormItem label="职位" {...formItemLayout} style={{marginTop:'-24px'}}>{selectUserInfo.roles ? selectUserInfo.roles[0] :''}</FormItem>
            <FormItem label="员工等级" {...formItemLayout} style={{marginTop:'-24px'}}>
              {
                selectingLevel ? <Select value={selectedLevel} placeholder="选择等级"
                                              onChange={this.handleLevelChange.bind(this)} style={{ width: '60%' }}>
                  {
                    levels.map(level => <Option key={level.id+""}>{level.name}</Option>)
                  }
                </Select> : selectUserInfo.level
              }
              {enableEdit ? <a style={{marginLeft:'8px'}} onClick={this.handleSaveOrEditLevel.bind(this, selectUserInfo)}>
                <Icon type={selectingLevel ? 'save':'edit'}/></a> : ''}
              {
                selectingLevel ? <a style={{marginLeft:'8px'}} onClick={this.handleCancelSaveLevel.bind(this)}><Icon type="close"/></a> : ''
              }
            </FormItem>
            <FormItem label="提成比例" {...formItemLayout} style={{marginTop:'-24px'}}>
              {createNew || selectingLevel ? <Input value={paymentPercentage} size="small" style={{width: '20%'}}
                                                    onChange={this.handlePaymentPercentageChange.bind(this)}/>
                : selectUserInfo.paymentPercentage} %
            </FormItem>
            {
              createNew ? '':<FormItem label="入职时间" {...formItemLayout} style={{marginTop:'-24px'}}>{selectUserInfo.hireTime}</FormItem>
            }
            {
              createNew ? '': <FormItem label="经办人" {...formItemLayout} style={{marginTop:'-24px'}}>{selectUserInfo.hirer}</FormItem>
            }
            {
              createNew ? '': <FormItem label="状态" {...formItemLayout} style={{marginTop:'-24px'}}>{selectUserInfo.status}</FormItem>
            }
            {
              selectUserInfo.dismissUser ? <FormItem label="离职经办人" {...formItemLayout} style={{marginTop:'-24px'}}>{selectUserInfo.dismissUser}</FormItem> : ''
            }
            {
              selectUserInfo.dismissUser ? <FormItem label="离职时间" {...formItemLayout} style={{marginTop:'-24px'}}>{selectUserInfo.dismissDate}</FormItem> : ''
            }
            {
              selectUserInfo.dismissUser ? <FormItem label="备注" {...formItemLayout} style={{marginTop:'-24px'}}>{selectUserInfo.dismissReason}</FormItem> : ''
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
        {
          selectUserInfo.dismissUser ? '':
            enableDelete && !createNew ? <Button type="primary" distroyOnClose
                                               style={{marginLeft: '8px', marginRight:'8px'}}
                                               icon="delete" size="small"
                                               onClick={this.handleOpenOrCloseDismissModal.bind(this)}>
              员工离职</Button> : ''
        }
      </div>
      <Modal visible={dismissingUser} title="离职原因"
             onCancel={this.handleOpenOrCloseDismissModal.bind(this)}
             okText="确认" cancelText="取消"
             onOk={this.handleDismissUser.bind(this, selectUserInfo)}>
        <TextArea value={dismissReason} onChange={this.handleDismissReasonChange.bind(this)}/>
      </Modal>
    </CommonSpin>);
  }
}
