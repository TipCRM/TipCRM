package com.tipcrm.cache;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.text.html.HTMLDocument;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.tipcrm.dao.entity.Role;
import org.springframework.util.CollectionUtils;

public class RoleCache {

    private static List<Role> allRole = new ArrayList<>();

    /**
     * Map <userId, roles>
     */
    private static Map<Integer, List<Role>> userRoles = Maps.newHashMap();

    public static void addRole(Role role) {
        if (role != null) {
            allRole.add(role);
        }
    }

    public static void updateRole(Role role) {
        if (role != null) {
            Integer index = -1;
            for (int i = 0; i < allRole.size(); i++) {
                if (allRole.get(i).getId().equals(role.getId())) {
                    index = i;
                    break;
                }
            }
            if (index >= 0) {
                allRole.set(index, role);
            }
        }
    }

    public static void addOrUpdateRoles(Integer userId, List<Role> roles) {
        if (CollectionUtils.isEmpty(userRoles)) {
            userRoles = Maps.newHashMap();
        }
        userRoles.put(userId, roles);
    }

    public static void deleteRole(Integer roleId) {
        removeRole(roleId, allRole);
        for (Map.Entry<Integer, List<Role>> entry : userRoles.entrySet()) {
            List<Role> roles = entry.getValue();
            removeRole(roleId, roles);
        }
    }

    private static boolean removeRole(Integer roleId, List<Role> roles) {
        if (!CollectionUtils.isEmpty(roles)) {
            Iterator<Role> it = roles.iterator();
            while (it.hasNext()) {
                if (it.next().getId().equals(roleId)) {
                    it.remove();
                    return true;
                }
            }
        }
        return false;
    }

    public static void addOrUpdateRoles(Integer userId, Role role) {
        addOrUpdateRoles(userId, Lists.newArrayList(role));
    }

    public static void pushRoles(Integer userId, List<Role> roles) {
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
        List<Role> currentRoles = userRoles.get(userId);
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

    public static List<Role> getRoles(Integer userId) {
        if (CollectionUtils.isEmpty(userRoles)) {
            return new ArrayList<>();
        }
        return userRoles.get(userId);
    }

    public static List<Role> getAllRole() {
        return allRole;
    }

    public static void setAllRole(List<Role> allRole) {
        RoleCache.allRole = allRole;
    }

    public static Map<Integer, List<Role>> getUserRoles() {
        return userRoles;
    }

    public static void setUserRoles(Map<Integer, List<Role>> userRoles) {
        RoleCache.userRoles = userRoles;
    }

    public static Role getRoleByName(String name) {
        List<Role> roles = RoleCache.getAllRole();
        for (Role role : roles) {
            if (role.getName().equals(name)) {
                return role;
            }
        }
        return null;
    }

    public static Role getRoleById(Integer id) {
        List<Role> roles = RoleCache.getAllRole();
        for (Role role : roles) {
            if (role.getId().equals(id)) {
                return role;
            }
        }
        return null;
    }
}
