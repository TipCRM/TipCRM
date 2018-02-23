package com.tipcrm.cache;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.util.CollectionUtils;

public class PermissionCache {

    /**
     * Map <userId, permissions>
     */
    private static Map<Integer, Set<String>> userPermissions = Maps.newHashMap();;
    
    public static void addOrUpdatePermissions(Integer userId, Collection<String> permissions) {
        if (CollectionUtils.isEmpty(userPermissions)) {
            userPermissions = Maps.newHashMap();;
        }
        userPermissions.put(userId, Sets.newHashSet(permissions));
    }
    
    public static void addOrUpdatePermissions(Integer userId, String permission) {
        addOrUpdatePermissions(userId, Sets.newHashSet(permission));
    }
    
    public static void pushPermissions(Integer userId, Collection<String> permissions) {
        if (CollectionUtils.isEmpty(userPermissions)) {
            userPermissions = Maps.newHashMap();;
        }
        if (CollectionUtils.isEmpty(userPermissions.get(userId))) {
            addOrUpdatePermissions(userId, permissions);
        } else {
            userPermissions.get(userId).addAll(Sets.newHashSet(permissions));
        }
    }
    
    public static void popPermissions(Integer userId, Collection<String> permissions) {
        if (CollectionUtils.isEmpty(userPermissions)) {
            return;
        }
        Set<String> currentPermissions = userPermissions.get(userId);
        if (CollectionUtils.isEmpty(currentPermissions)) {
            return;
        }
        Iterator<String> it = currentPermissions.iterator();
        while (it.hasNext()) {
            if (permissions.contains(it.next())) {
                it.remove();
            }
        }
    }
    
    public static Set<String> getPermissions(Integer userId) {
        if (CollectionUtils.isEmpty(userPermissions)) {
            return Sets.newHashSet();
        }
        return userPermissions.get(userId);
    }
}
