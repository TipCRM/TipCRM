/**
 * Created by mosesc on 03/21/18.
 */
import React from 'react';
import {Card, Modal} from 'antd';
import styles from './Index.css';
const {Meta} = Card;

export default class CardCell extends React.Component{

  state={
    showModal: false,
  };

  handlerCardClick(active){
    this.setState({
      showModal: true,
    });
  }
  handlerModalCancel(){
    this.setState({
      showModal: false,
    });
  }

  render(){
    const {title, content, active} = this.props;
    const iconUrl = active ? "https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png" : "https://os.alipayobjects.com/rmsportal/QBnOOoLaAfKPirc.png";
    const {showModal} = this.state;
    const icon = (<img src={iconUrl} className={styles.iconStyle}/>) ;
    const titles = (<div className={styles.titleStyle}>{title}</div>);
    return(
      <div className={active ? styles['hvr-grow-shadow'] : styles.disabelCell}>
        <Card  onClick={active ? this.handlerCardClick.bind(this) : {}} className={styles.cardStyle} cover={icon}>
          {titles}
        </Card>
        <Modal width="80%" title={title}
               visible={showModal}
               onCancel={this.handlerModalCancel.bind(this)}
               footer={null} destroyOnClose={true}>
          {content}
        </Modal>
      </div>);
  }
}
