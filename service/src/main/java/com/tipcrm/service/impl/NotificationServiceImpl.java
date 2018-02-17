package com.tipcrm.service.impl;
import java.util.Date;

import com.tipcrm.constant.Constants;
import com.tipcrm.constant.ListBoxCategory;
import com.tipcrm.constant.NotificationReadStatus;
import com.tipcrm.constant.NotificationType;
import com.tipcrm.dao.entity.ListBox;
import com.tipcrm.dao.entity.Notification;
import com.tipcrm.dao.repository.ListBoxRepository;
import com.tipcrm.dao.repository.NotificationRepository;
import com.tipcrm.dao.repository.UserRepository;
import com.tipcrm.service.NotificationService;
import com.tipcrm.service.WebContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ListBoxRepository listBoxRepository;

    @Autowired
    private WebContext webContext;

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Integer createNotification(Integer toId, String subject, String content, NotificationType type) {
        Notification notification = new Notification();
        notification.setToUser(userRepository.findOne(toId));
        notification.setSubject(subject);
        notification.setContent(content);
        ListBox notificationType = listBoxRepository.findByCategoryNameAndName(ListBoxCategory.NOTIFICATION_TYPE.name(), type.name());
        ListBox unread = listBoxRepository.findByCategoryNameAndName(ListBoxCategory.NOTIFICATION_READ_STATUS.name(), NotificationReadStatus.UNREAD.name());
        notification.setType(notificationType);
        notification.setReadStatus(unread);
        if (NotificationType.SYSTEM_NOTIFICATION.equals(type)) {
            notification.setEntryUser(userRepository.findByUserName(Constants.User.SYSTEM).get(0));
        } else {
            notification.setEntryUser(webContext.getCurrentUser());
        }
        notification.setEntryTime(new Date());
        notificationRepository.save(notification);
        return notification.getId();
    }
}
