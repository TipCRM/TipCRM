package com.tipcrm.service;
import java.util.Map;
import java.util.Set;

import com.tipcrm.bo.PermissionBo;
import com.tipcrm.bo.RoleBo;

public interface RoleService {

    Set<RoleBo> getRolesByUserId(Integer userId);

    Map<Integer, Set<PermissionBo>> getAllRolePermissionMap();
}
