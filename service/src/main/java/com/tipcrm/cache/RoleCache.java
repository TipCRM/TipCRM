package com.tipcrm.cache;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.tipcrm.bo.RoleBasicBo;
import org.springframework.util.CollectionUtils;

public class RoleCache {

    private static List<RoleBasicBo> allRole = new ArrayList<>();

    /**
     * Map <userId, roles>
     */
    private static Map<Integer, Set<RoleBasicBo>> userRoles = Maps.newHashMap();

    public static void addOrUpdateRoles(Integer userId, Set<RoleBasicBo> roles) {
        if (CollectionUtils.isEmpty(userRoles)) {
            userRoles = Maps.newHashMap();
        }
        userRoles.put(userId, roles);
    }

    public static void addOrUpdateRoles(Integer userId, RoleBasicBo role) {
        addOrUpdateRoles(userId, Sets.newHashSet(role));
    }

    public static void pushRoles(Integer userId, Set<RoleBasicBo> roles) {
        if (CollectionUtils.isEmpty(userRoles)) {
            userRoles = Maps.newHashMap();;
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
        Set<RoleBasicBo> currentRoles = userRoles.get(userId);
        if (CollectionUtils.isEmpty(currentRoles)) {
            return;
        }
        Iterator<RoleBasicBo> it = currentRoles.iterator();
        while (it.hasNext()) {
            if (roleIds.contains(it.next().getId())) {
                it.remove();
            }
        }
    }

    public static Set<RoleBasicBo> getRoles(Integer userId) {
        if (CollectionUtils.isEmpty(userRoles)) {
            return new HashSet<>();
        }
        return userRoles.get(userId);
    }

    public static List<RoleBasicBo> getAllRole() {
        return allRole;
    }

    public static void setAllRole(List<RoleBasicBo> allRole) {
        RoleCache.allRole = allRole;
    }

    public static Map<Integer, Set<RoleBasicBo>> getUserRoles() {
        return userRoles;
    }

    public static void setUserRoles(Map<Integer, Set<RoleBasicBo>> userRoles) {
        RoleCache.userRoles = userRoles;
    }
}
