package com.tipcrm.service.impl;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.tipcrm.bo.SimpleNotificationBo;
import com.tipcrm.cache.NotificationCache;
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
import org.springframework.util.CollectionUtils;

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
        notification = notificationRepository.save(notification);
        NotificationCache.push(toId, notification);
        return notification.getId();
    }

    @Override
    public List<SimpleNotificationBo> getMyRealTimeNotifications() {
        return convertToSimpleNotificationBos(NotificationCache.pop(webContext.getCurrentUserId()));
    }

    private List<SimpleNotificationBo> convertToSimpleNotificationBos(List<Notification> notifications) {
        List<SimpleNotificationBo> simpleNotificationBos = Lists.newArrayList();
        if (CollectionUtils.isEmpty(notifications)) {
            return null;
        }
        for (Notification notification : notifications) {
            SimpleNotificationBo simpleNotificationBo = convertToSimpleNotificationBo(notification);
            if (simpleNotificationBo != null) {
                simpleNotificationBos.add(simpleNotificationBo);
            }
        }
        return simpleNotificationBos;
    }

    private SimpleNotificationBo convertToSimpleNotificationBo(Notification notification) {
        if (notification == null) {
            return null;
        }
        SimpleNotificationBo simpleNotificationBo = new SimpleNotificationBo();
        simpleNotificationBo.setFrom(notification.getEntryUser().getUserName());
        simpleNotificationBo.setNotificationId(notification.getId());
        simpleNotificationBo.setSubject(notification.getSubject());
        return simpleNotificationBo;
    }
}
