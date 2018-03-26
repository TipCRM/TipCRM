/**
 * Created by mosesc on 03/26/18.
 */
import React from 'react';
import {Tag} from 'antd';
const {CheckableTag} = Tag;

export default class TipCheckableTag extends React.Component{
  render(){
    const {tagChecked, handleTagChange, children} = this.props;
    return(
        <CheckableTag checked={tagChecked} onChange={handleTagChange}>{children}</CheckableTag>
    );
  }
}
