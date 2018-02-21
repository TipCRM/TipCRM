package com.tipcrm.cache;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.util.CollectionUtils;

public class RoleCache {

    /**
     * Map <userId, roles>
     */
    private static Map<Integer, Set<String>> userRoles = Maps.newHashMap();;

    public static void addOrUpdateRoles(Integer userId, Collection<String> roles) {
        if (CollectionUtils.isEmpty(userRoles)) {
            userRoles = Maps.newHashMap();;
        }
        userRoles.put(userId, Sets.newHashSet(roles));
    }

    public static void addOrUpdateRoles(Integer userId, String role) {
        addOrUpdateRoles(userId, Sets.newHashSet(role));
    }

    public static void pushRoles(Integer userId, Collection<String> roles) {
        if (CollectionUtils.isEmpty(userRoles)) {
            userRoles = Maps.newHashMap();;
        }
        if (CollectionUtils.isEmpty(userRoles.get(userId))) {
            addOrUpdateRoles(userId, roles);
        } else {
            userRoles.get(userId).addAll(Sets.newHashSet(roles));
        }
    }

    public static void popRoles(Integer userId, Collection<String> roles) {
        if (CollectionUtils.isEmpty(userRoles)) {
            return;
        }
        Set<String> currentRoles = userRoles.get(userId);
        if (CollectionUtils.isEmpty(currentRoles)) {
            return;
        }
        Iterator<String> it = currentRoles.iterator();
        while (it.hasNext()) {
            if (roles.contains(it.next())) {
                it.remove();
            }
        }
    }

    public static Set<String> getRoles(Integer userId) {
        if (CollectionUtils.isEmpty(userRoles)) {
            return Sets.newHashSet();
        }
        return userRoles.get(userId);
    }
}
