/**
 * Created by mosesc on 03/26/18.
 */
import React from 'react';
import TipCheckableTag from './TipCheckableTag';
import styles from './Index.less';

export default class CommonTagGroup extends React.Component{
  render(){
    const {tags} = this.props;
    return(
      <div className={styles.tagGroup}>快速操作：
        {
          tags ? tags.map(tag =>{
            return( <TipCheckableTag tagChecked={tag.checked} handleTagChange={tag.handleTagChange}>{tag.content}</TipCheckableTag>)
          }) : tags
        }
      </div>);
  }
}
