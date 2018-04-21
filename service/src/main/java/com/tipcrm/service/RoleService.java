package com.tipcrm.service;

import com.tipcrm.bo.RoleBasicBo;
import com.tipcrm.bo.RoleBo;
import com.tipcrm.bo.SaveRoleBo;
import com.tipcrm.dao.entity.Permission;
import com.tipcrm.dao.entity.Role;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RoleService {

    List<Role> getRolesByUserId(Integer userId);

    List<RoleBasicBo> getMyRoles();

    Role findById(Integer roleId);

    Map<Integer, Set<Permission>> getAllRolePermissionMap();

    List<RoleBo> getAllRoles();

    Integer saveRole(SaveRoleBo saveRoleBo);

    void updateRole(SaveRoleBo saveRoleBo);

    void deleteRole(Integer roleId);

    void assignRoleToUser(Integer userId, Set<Integer> roleIds);
}
