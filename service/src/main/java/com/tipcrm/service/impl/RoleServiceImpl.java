package com.tipcrm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;
import com.tipcrm.bo.PermissionBo;
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
    public Set<RoleBo> getRolesByUserId(Integer userId) {
        Set<RoleBo> roles = RoleCache.getRoles(userId);
        if (CollectionUtils.isEmpty(roles)) {
            User user = userRepository.findOne(userId);
            roles = Sets.newHashSet(RoleBo.toRoleBos(user.getRoles()));
            RoleCache.addOrUpdateRoles(userId, roles);
        }
        return roles;
    }

    @Override
    public Map<Integer, Set<PermissionBo>> getAllRolePermissionMap(){
        List<Role> roles = roleRepository.findAll();
        Map<Integer,Set<PermissionBo>> rolePermissions = new HashMap<>();
        for (Role role : roles) {
            List<Permission> permissions = new ArrayList<>();
            List<RolePermission> rolePermissionList = role.getRolePermissions();
            if (!CollectionUtils.isEmpty(rolePermissionList)) {
                permissions.addAll(rolePermissionList.stream().map(rp -> rp.getPermission()).collect(Collectors.toList()));
            }
            rolePermissions.put(role.getId(), Sets.newHashSet(PermissionBo.toPermissionBos(permissions)));
        }
        return rolePermissions;
    }
}
