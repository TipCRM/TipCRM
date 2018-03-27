package com.tipcrm.cache;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.tipcrm.bo.PermissionBo;
import com.tipcrm.bo.PermissionGroupBo;
import com.tipcrm.util.MyCollectionUtils;
import org.springframework.util.CollectionUtils;

public class PermissionCache {

    /**
     * Map <roleId, permissions>
     */
    private static Map<Integer, Set<PermissionBo>> rolePermissions = Maps.newHashMap();

    private static List<PermissionGroupBo> allPermissionGroups = new ArrayList<>();

    public static void addOrUpdatePermissions(Integer roleId, Set<PermissionBo> permissions) {
        if (CollectionUtils.isEmpty(rolePermissions)) {
            rolePermissions = Maps.newHashMap();
        }
        rolePermissions.put(roleId, permissions);
    }

    public static void addOrUpdatePermissions(Integer roleId, PermissionBo permission) {
        addOrUpdatePermissions(roleId, Sets.newHashSet(permission));
    }

    public static void pushPermissions(Integer roleId, Set<PermissionBo> permissions) {
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
        Set<PermissionBo> currentPermissions = rolePermissions.get(roleId);
        if (CollectionUtils.isEmpty(currentPermissions)) {
            return;
        }
        Iterator<PermissionBo> it = currentPermissions.iterator();
        while (it.hasNext()) {
            if (permissionIds.contains(it.next().getId())) {
                it.remove();
            }
        }
    }

    public static Set<PermissionBo> getPermissions(Integer roleId) {
        if (CollectionUtils.isEmpty(rolePermissions)) {
            return new HashSet<>();
        }
        return rolePermissions.get(roleId);
    }

    public static Set<PermissionBo> getPermissions(List<Integer> roleIds) {
        if (CollectionUtils.isEmpty(rolePermissions) || CollectionUtils.isEmpty(roleIds)) {
            return new HashSet<>();
        }
        Set<PermissionBo> permissionBos = new HashSet<>();
        for (Integer roleId : roleIds) {
            permissionBos.addAll(getPermissions(roleId));
        }
        return permissionBos;
    }

    public static Map<Integer, Set<PermissionBo>> getRolePermissions() {
        return rolePermissions;
    }

    public static void setRolePermissions(Map<Integer, Set<PermissionBo>> rolePermissions) {
        PermissionCache.rolePermissions = rolePermissions;
    }

    public static List<PermissionGroupBo> getAllPermissionGroups() {
        return MyCollectionUtils.depCopy(allPermissionGroups);
    }

    public static void setAllPermissionGroups(List<PermissionGroupBo> allPermissionGroups) {
        PermissionCache.allPermissionGroups = allPermissionGroups;
    }
}
