package com.tipcrm.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;
import com.tipcrm.bo.RoleBo;
import com.tipcrm.cache.RoleCache;
import com.tipcrm.dao.entity.Permission;
import com.tipcrm.dao.entity.Role;
import com.tipcrm.dao.entity.RolePermission;
import com.tipcrm.dao.entity.User;
import com.tipcrm.dao.repository.RoleRepository;
import com.tipcrm.dao.repository.UserRepository;
import com.tipcrm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Set<Role> getRolesByUserId(Integer userId) {
        Set<Role> roles = RoleCache.getRoles(userId);
        if (CollectionUtils.isEmpty(roles)) {
            User user = userRepository.findOne(userId);
            roles = Sets.newHashSet(user.getRoles());
            RoleCache.addOrUpdateRoles(userId, roles);
        }
        return roles;
    }

    @Override
    public Role findById(Integer roleId) {
        List<Role> roles = RoleCache.getAllRole();
        for (Role role : roles) {
            if (role.getId().equals(roleId)) {
                return role;
            }
        }
        return null;
    }

    @Override
    public Map<Integer, Set<Permission>> getAllRolePermissionMap() {
        List<Role> roles = roleRepository.findAll();
        Map<Integer, Set<Permission>> rolePermissions = new HashMap<>();
        for (Role role : roles) {
            Set<Permission> permissions = new HashSet<>();
            List<RolePermission> rolePermissionList = role.getRolePermissions();
            if (!CollectionUtils.isEmpty(rolePermissionList)) {
                permissions.addAll(rolePermissionList.stream().map(rp -> rp.getPermission()).collect(Collectors.toList()));
            }
            rolePermissions.put(role.getId(), permissions);
        }
        return rolePermissions;
    }

    @Override
    public List<RoleBo> getAllRoles() {
        List<Role> roles = RoleCache.getAllRole();
        return RoleBo.toRoleBos(roles);
    }
}
