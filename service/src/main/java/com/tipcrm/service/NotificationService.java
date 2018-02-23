package com.tipcrm.service;
import java.util.List;

import com.tipcrm.bo.NotificationBo;
import com.tipcrm.bo.QueryRequestBo;
import com.tipcrm.bo.QueryResultBo;
import com.tipcrm.bo.SimpleNotificationBo;
import com.tipcrm.constant.NotificationType;
import com.tipcrm.exception.BizException;
import com.tipcrm.exception.QueryException;

public interface NotificationService {

    Integer createNotification(Integer toId, String subject, String content, NotificationType type);

    List<SimpleNotificationBo> getMyRealTimeNotifications();

    QueryResultBo<NotificationBo> getMyNotifications(QueryRequestBo queryRequestBo) throws QueryException, BizException;
}
