package com.tipcrm.cache;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.tipcrm.dao.entity.Permission;
import com.tipcrm.dao.entity.PermissionGroup;
import org.springframework.util.CollectionUtils;

public class PermissionCache {

    /**
     * Map <roleId, permissions>
     */
    private static Map<Integer, Set<Permission>> rolePermissions = Maps.newHashMap();

    private static List<PermissionGroup> allPermissionGroups = new ArrayList<>();

    private static List<Permission> deactivePermissions = new ArrayList<>();

    public static List<Permission> getDeactivePermissions() {
        return deactivePermissions;
    }

    public static void setDeactivePermissions(List<Permission> deactivePermission) {
        deactivePermissions = deactivePermission;
    }

    public static void addOrUpdatePermissions(Integer roleId, Set<Permission> permissions) {
        if (CollectionUtils.isEmpty(rolePermissions)) {
            rolePermissions = Maps.newHashMap();
        }
        rolePermissions.put(roleId, permissions);
    }

    public static void addOrUpdatePermissions(Integer roleId, Permission permission) {
        addOrUpdatePermissions(roleId, Sets.newHashSet(permission));
    }

    public static void pushPermissions(Integer roleId, Set<Permission> permissions) {
        if (CollectionUtils.isEmpty(rolePermissions)) {
            rolePermissions = Maps.newHashMap();
        }
        if (CollectionUtils.isEmpty(rolePermissions.get(roleId))) {
            addOrUpdatePermissions(roleId, permissions);
        } else {
            rolePermissions.get(roleId).addAll((permissions));
        }
    }

    public static void popPermissions(Integer roleId, Set<Integer> permissionIds) {
        if (CollectionUtils.isEmpty(rolePermissions)) {
            return;
        }
        Set<Permission> currentPermissions = rolePermissions.get(roleId);
        if (CollectionUtils.isEmpty(currentPermissions)) {
            return;
        }
        Iterator<Permission> it = currentPermissions.iterator();
        while (it.hasNext()) {
            if (permissionIds.contains(it.next().getId())) {
                it.remove();
            }
        }
    }

    public static Set<Permission> getPermissions(Integer roleId) {
        if (CollectionUtils.isEmpty(rolePermissions)) {
            return new HashSet<>();
        }
        return rolePermissions.get(roleId);
    }

    public static Set<Permission> getPermissions(List<Integer> roleIds) {
        if (CollectionUtils.isEmpty(rolePermissions) || CollectionUtils.isEmpty(roleIds)) {
            return new HashSet<>();
        }
        Set<Permission> permissions = new HashSet<>();
        for (Integer roleId : roleIds) {
            permissions.addAll(getPermissions(roleId));
        }
        return permissions;
    }

    public static Map<Integer, Set<Permission>> getRolePermissions() {
        return rolePermissions;
    }

    public static void setRolePermissions(Map<Integer, Set<Permission>> rolePermissions) {
        PermissionCache.rolePermissions = rolePermissions;
    }

    public static List<PermissionGroup> getAllPermissionGroups() {
        return allPermissionGroups;
    }

    public static void setAllPermissionGroups(List<PermissionGroup> allPermissionGroups) {
        PermissionCache.allPermissionGroups = allPermissionGroups;
    }
}
