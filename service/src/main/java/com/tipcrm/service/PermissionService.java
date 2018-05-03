package com.tipcrm.service;

import java.util.List;
import java.util.Set;

import com.tipcrm.bo.MenuPermissionBo;
import com.tipcrm.dao.entity.Permission;

public interface PermissionService {

    Set<String> getPermissionValuesByUserId(Integer userId);

    Set<String> getPermissionNamesByUserId(Integer userId);

    Set<Integer> getPermissionIdsByUserId(Integer userId);

    List<MenuPermissionBo> getMenuPermissionsByUserId(Integer userId);

    List<MenuPermissionBo> getPermissionsByRoleId(Integer roleId);

    void updateRolePermissions(Integer roleId, Set<Integer> permissionIds);

    List<Permission> findByIdIn(Set<Integer> ids);

    List<Permission> flatPermission(List<Permission> permissions);

    List<String> getMyPermission(Integer menuId);

    List<Permission> getPermissionById(List<Integer> ids);
}
