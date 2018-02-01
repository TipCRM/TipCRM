export const getNotices = (req, res) => {
  res.json([
    {
      id: '000000001',
      avatar: 'https://gw.alipayobjects.com/zos/rmsportal/ThXAXghbEsBCCSDihZxY.png',
      title: '你审核已通过',
      datetime: '2017-08-09',
      type: '通知',
    },
    {
      id: '000000002',
      avatar: 'https://gw.alipayobjects.com/zos/rmsportal/OKJXDXrmkNshAMvwtvhu.png',
      title: '小窍门客户转移给了你',
      datetime: '2017-08-08',
      type: '通知',
    },
    {
      id: '000000009',
      title: '任务名称',
      description: '任务需要在 2017-01-12 20:00 前启动',
      status: 'todo',
      type: '待办',
    },
    {
      id: '000000010',
      title: '当月任务',
      description: '还差2,000完成任务',
      extra: '马上到期',
      status: 'urgent',
      type: '待办',
    },
    {
      id: '000000011',
      title: '客户关系维护',
      description: '正在追踪客户小窍门',
      extra: '已耗时 8 天',
      status: 'doing',
      type: '待办',
    },
    {
      id: '000000012',
      title: '意向客户汇总',
      description: '大灰、小白和李老师',
      extra: '进行中',
      status: 'processing',
      type: '待办',
    },
  ]);
};
export default {
  getNotices,
};
