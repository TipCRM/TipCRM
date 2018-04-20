/**
 * Created by mosesc on 04/20/18.
 */
import React from 'react';
import {connect} from 'dva';
import {Avatar, Upload, Icon, Modal, message} from 'antd';
import {uploadAvatar} from '../../../services/api'

@connect(({loading, user}) =>({
  currentUserloading : loading.effects['user/getCurrentUser'],
  currentUser: user.currentUser,
}))

export default class UserCenterPanel extends React.Component{
  state={
    avatar: this.props.currentUser.avatar,
    showChangeAvatar: false,
    loading:false,
  }

  componentDidMount(){
    //const {dispatch} = this.props;
    //dispatch({
    //  type: 'user/getCurrentUser'
    //});
    //this.setState({
    //  avatar: ,
    //});
  }
  getBase64(img, callback) {
    const reader = new FileReader();
    reader.addEventListener('load', () => callback(reader.result));
    reader.readAsDataURL(img);
  }

  beforeUpload(file) {
    const isJPG = file.type === 'image/jpeg';
    if (!isJPG) {
      message.error('You can only upload JPG file!');
    }
    const isLt2M = file.size / 1024 / 1024 < 2;
    if (!isLt2M) {
      message.error('Image must smaller than 2MB!');
    }
    return isJPG && isLt2M;
  }


  handleShowChangeAvatarModal(){
    this.setState({
      showChangeAvatar: true,
    });
  }
  handleCloseChangeAvatarModal(){
    this.setState({
      showChangeAvatar: false,
    });
  }
  handleChange = (info) => {
    console.log("info: ", info);
    if (info.file.status !== 'uploading') {
      console.log(info.file, info.fileList);
    }
    if (info.file.status === 'done') {
      message.success(`${info.file.name} file uploaded successfully`);
    } else if (info.file.status === 'error') {
      message.error(`${info.file.name} file upload failed.`);
    }
  }

  render(){
    const {currentUser} = this.props;
    const {avatar, showChangeAvatar} = this.state;
    const uploadButton = (
      <div>
        <Icon type={this.state.loading ? 'loading' : 'plus'} />
        <div className="ant-upload-text">Upload</div>
      </div>
    );

    return(<div>
      <a onClick={this.handleShowChangeAvatarModal.bind(this)}>
        <Avatar size="large" src={avatar}/>
      </a>
      {currentUser.name}
      <Modal footer="" visible={showChangeAvatar} title="上传头像" width="60%"
             onCancel={this.handleCloseChangeAvatarModal.bind(this)} destroyOnClose>
        <Upload
          name="avatar"
          listType="picture-card"
          className="avatar-uploader"
          headers = "Content-Type: multipart/form-data;"
          showUploadList={false}
          action="http://192.168.87.32:8080/tipcrm/avatar"
        >
          {avatar ? <img src={avatar} alt="" /> : uploadButton}
        </Upload>
      </Modal>
    </div>);
  }
}
