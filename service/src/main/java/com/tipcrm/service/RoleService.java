package com.tipcrm.service;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tipcrm.bo.PermissionBo;
import com.tipcrm.bo.RoleBasicBo;
import com.tipcrm.bo.RoleBo;

public interface RoleService {

    Set<RoleBasicBo> getRolesByUserId(Integer userId);

    Map<Integer, Set<PermissionBo>> getAllRolePermissionMap();

    List<RoleBo> getAllRoles();
}
