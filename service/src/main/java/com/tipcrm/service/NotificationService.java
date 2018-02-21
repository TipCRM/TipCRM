package com.tipcrm.service;
import java.util.List;

import com.tipcrm.bo.SimpleNotificationBo;
import com.tipcrm.constant.NotificationType;

public interface NotificationService {

    Integer createNotification(Integer toId, String subject, String content, NotificationType type);

    List<SimpleNotificationBo> getMyRealTimeNotifications();
}
