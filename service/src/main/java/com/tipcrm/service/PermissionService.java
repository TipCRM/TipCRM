package com.tipcrm.service;
import java.util.List;
import java.util.Set;

import com.tipcrm.bo.PermissionGroupBo;

public interface PermissionService {

    Set<String> getPermissionValueListByUserId(Integer userId);

    List<PermissionGroupBo> getAllGroup();

    List<PermissionGroupBo> getPermissionsByUserId(Integer userId);

    List<PermissionGroupBo> getPermissionsByRoleId(Integer roleId);

    void updateRolePermissions(Integer roleId, Set<Integer> permissionIds);
}
