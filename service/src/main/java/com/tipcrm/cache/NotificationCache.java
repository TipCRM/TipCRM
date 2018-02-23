package com.tipcrm.cache;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tipcrm.dao.entity.Notification;
import org.springframework.util.CollectionUtils;

public class NotificationCache {
    /**
     * Map<userId, Map<notificationId, notification>>
     */
    private static Map<Integer, Map<Integer, Notification>> notifications = Maps.newHashMap();

    public static void push(Integer userId, Notification notification) {
        if (CollectionUtils.isEmpty(notifications)) {
            notifications = Maps.newHashMap();
        }
        Map<Integer, Notification> myNotifications = notifications.get(userId);
        if (CollectionUtils.isEmpty(myNotifications)) {
            notifications.put(userId, Maps.newHashMap());
        }
        notifications.get(userId).put(notification.getId(), notification);
    }

    public static Notification pop(Integer userId, Integer notificationId) {
        if (CollectionUtils.isEmpty(notifications)) {
            return null;
        }
        Map<Integer, Notification> myNotifications = notifications.get(userId);
        if (CollectionUtils.isEmpty(myNotifications)) {
            return null;
        }
        Notification notification = myNotifications.remove(notificationId);
        return notification;
    }

    public static List<Notification> pop(Integer userId) {
        if (CollectionUtils.isEmpty(notifications)) {
            return null;
        }
        Map<Integer, Notification> myNotifications = notifications.remove(userId);
        return Lists.newArrayList(myNotifications.values());
    }

    public static List<Notification> getByUserId(Integer userId) {
        if (CollectionUtils.isEmpty(notifications)) {
            return Lists.newArrayList();
        }
        return Lists.newArrayList(notifications.get(userId).values());
    }
}
