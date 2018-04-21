package com.tipcrm.service;

import com.tipcrm.bo.NotificationBo;
import com.tipcrm.bo.QueryRequestBo;
import com.tipcrm.bo.QueryResultBo;
import com.tipcrm.bo.SimpleNotificationBo;
import com.tipcrm.constant.NotificationType;

import java.util.List;

public interface NotificationService {

    Integer createNotification(Integer toId, String subject, String content, NotificationType type);

    List<SimpleNotificationBo> getMyRealTimeNotifications();

    QueryResultBo<NotificationBo> getMyNotifications(QueryRequestBo queryRequestBo);

    NotificationBo getNotificationById(Integer notificationId);
}
