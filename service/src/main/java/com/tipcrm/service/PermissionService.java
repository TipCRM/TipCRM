package com.tipcrm.service;
import java.util.List;
import java.util.Set;

import com.tipcrm.bo.PermissionGroupBo;
import com.tipcrm.dao.entity.Permission;

public interface PermissionService {

    Set<String> getPermissionValuesByUserId(Integer userId);

    Set<Integer> getPermissionIdsByUserId(Integer userId);

    List<PermissionGroupBo> getAllGroup();

    List<PermissionGroupBo> getPermissionsByUserId(Integer userId);

    List<PermissionGroupBo> getPermissionsByRoleId(Integer roleId);

    void updateRolePermissions(Integer roleId, Set<Integer> permissionIds);

    List<Permission> flatPermission(List<Permission> permissions);
}
