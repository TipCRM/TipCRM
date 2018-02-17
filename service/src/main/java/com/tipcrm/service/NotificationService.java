package com.tipcrm.service;
import com.tipcrm.constant.NotificationType;

public interface NotificationService {

    Integer createNotification(Integer toId, String subject, String content, NotificationType type);
}
