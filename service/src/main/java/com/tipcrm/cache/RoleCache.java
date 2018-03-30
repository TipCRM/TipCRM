package com.tipcrm.cache;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.tipcrm.dao.entity.Role;
import org.springframework.util.CollectionUtils;

public class RoleCache {

    private static List<Role> allRole = new ArrayList<>();

    /**
     * Map <userId, roles>
     */
    private static Map<Integer, Set<Role>> userRoles = Maps.newHashMap();

    public static void addOrUpdateRoles(Integer userId, Set<Role> roles) {
        if (CollectionUtils.isEmpty(userRoles)) {
            userRoles = Maps.newHashMap();
        }
        userRoles.put(userId, roles);
    }

    public static void addOrUpdateRoles(Integer userId, Role role) {
        addOrUpdateRoles(userId, Sets.newHashSet(role));
    }

    public static void pushRoles(Integer userId, Set<Role> roles) {
        if (CollectionUtils.isEmpty(userRoles)) {
            userRoles = Maps.newHashMap();
        }
        if (CollectionUtils.isEmpty(userRoles.get(userId))) {
            addOrUpdateRoles(userId, roles);
        } else {
            userRoles.get(userId).addAll(roles);
        }
    }

    public static void popRoles(Integer userId, List<Integer> roleIds) {
        if (CollectionUtils.isEmpty(userRoles)) {
            return;
        }
        Set<Role> currentRoles = userRoles.get(userId);
        if (CollectionUtils.isEmpty(currentRoles)) {
            return;
        }
        Iterator<Role> it = currentRoles.iterator();
        while (it.hasNext()) {
            if (roleIds.contains(it.next().getId())) {
                it.remove();
            }
        }
    }

    public static Set<Role> getRoles(Integer userId) {
        if (CollectionUtils.isEmpty(userRoles)) {
            return new HashSet<>();
        }
        return userRoles.get(userId);
    }

    public static List<Role> getAllRole() {
        return allRole;
    }

    public static void setAllRole(List<Role> allRole) {
        RoleCache.allRole = allRole;
    }

    public static Map<Integer, Set<Role>> getUserRoles() {
        return userRoles;
    }

    public static void setUserRoles(Map<Integer, Set<Role>> userRoles) {
        RoleCache.userRoles = userRoles;
    }
}
