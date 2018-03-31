package com.tipcrm.service;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tipcrm.bo.RoleBo;
import com.tipcrm.bo.SaveRoleBo;
import com.tipcrm.dao.entity.Permission;
import com.tipcrm.dao.entity.Role;

public interface RoleService {

    List<Role> getRolesByUserId(Integer userId);

    Role findById(Integer roleId);

    Map<Integer, Set<Permission>> getAllRolePermissionMap();

    List<RoleBo> getAllRoles();

    Integer saveRole(SaveRoleBo saveRoleBo);

    void updateRole(SaveRoleBo saveRoleBo);

    void deleteRole(Integer roleId);
}
