/**
 * Created by mosesc on 02/02/18.
 */
import React from 'react';
import {connect} from 'dva';
import {Bar, Pie, yuan} from '../../components/Charts';
import {Spin, Row, Col, Card} from 'antd';
import styles from './Index.less';

@connect(({loading}) =>({loading}))
export default class Index extends React.PureComponent{

  render(){
    const salesData = [];
    for (let i = 0; i < 12; i += 1) {
      salesData.push({
        x: `${i + 1}月`,
        y: Math.floor(Math.random() * 1000) + 200,
      });
    }
    const salesPieData = [
      {
        x: '家用电器',
        y: 4544,
      },
      {
        x: '食用酒水',
        y: 3321,
      },
      {
        x: '个护健康',
        y: 3113,
      },
      {
        x: '服饰箱包',
        y: 2341,
      },
      {
        x: '母婴产品',
        y: 1231,
      },
      {
        x: '其他',
        y: 1231,
      },
    ];

    const {loading} = this.props;

    const Info = ({ title, value, bordered }) => (
      <div className={styles.headerInfo}>
        <span>{title}</span>
        <p>{value}</p>
        {bordered && <em />}
      </div>
    );

    return(
      <div >
        <Card>
          <Row>
            <Col sm={6} xs={24}>
              <Info title="当月目标" value="￥20,000" bordered />
            </Col>
            <Col sm={6} xs={24}>
              <Info title="销售排名" value="1/20" bordered />
            </Col>
            <Col sm={6} xs={24}>
              <Info title="销售额" value="￥25,000" bordered/>
            </Col>
            <Col sm={6} xs={24}>
              <Info title="意向金额" value="￥25,000" />
            </Col>
          </Row>
        </Card>
          <Spin size={'default'}  tip="正在加载..." style={{fontSize:14,marginTop:'10%'}} spinning={false}>
            <Card style={{marginTop:'10px'}}>
              <div style={{marginTop:'10px',textAlign:'center'}}>
                <Row gutter={32}>
                  <Col span={12} style={{marginTop:'15px',background: '#fff'}}>
                    <Bar
                      height={200}
                      title="销售额趋势"
                      data={salesData}/>
                  </Col>
                  <Col span={12} style={{marginTop:'15px',background: '#fff'}}>
                    <Pie
                      hasLegend
                      title="销售额"
                      subTitle="销售额"
                      total={yuan(salesPieData.reduce((pre, now) => now.y + pre, 0))}
                      data={salesPieData}
                      valueFormat={val => yuan(val)}
                      height={200}
                    />
                  </Col>
                </Row>
              </div>
            </Card>
          </Spin>
      </div>
    );
  }

}
