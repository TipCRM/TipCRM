package com.tipcrm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;
import com.tipcrm.bo.RoleBo;
import com.tipcrm.bo.SaveRoleBo;
import com.tipcrm.cache.PermissionCache;
import com.tipcrm.cache.RoleCache;
import com.tipcrm.dao.entity.Permission;
import com.tipcrm.dao.entity.Role;
import com.tipcrm.dao.entity.RolePermission;
import com.tipcrm.dao.entity.User;
import com.tipcrm.dao.repository.RoleRepository;
import com.tipcrm.dao.repository.UserRepository;
import com.tipcrm.exception.BizException;
import com.tipcrm.service.PermissionService;
import com.tipcrm.service.RoleService;
import com.tipcrm.service.WebContext;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private WebContext webContext;

    @Autowired
    private PermissionService permissionService;

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

    @Override
    public Integer saveRole(SaveRoleBo saveRoleBo) {
        validateSaveRoleBo(saveRoleBo, true);
        User user = webContext.getCurrentUser();
        Date date = new Date();
        Role role = new Role();
        role.setName(saveRoleBo.getName());
        role.setDisplayName(saveRoleBo.getName());
        role.setEntryTime(date);
        role.setEntryUser(user);
        role.setEditable(true);
        List<Permission> permissions = null;
        if (!CollectionUtils.isEmpty(saveRoleBo.getPermissions())) {
            List<RolePermission> rolePermissions = new ArrayList<>();
            permissions = permissionService.getPermissionById(saveRoleBo.getPermissions());
            for (Permission permission : permissions) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRole(role);
                rolePermission.setPermission(permission);
                rolePermission.setDeletable(true);
                rolePermission.setEntryUser(user);
                rolePermission.setEntryTime(date);
                rolePermissions.add(rolePermission);
            }
            role.setRolePermissions(rolePermissions);
        }
        roleRepository.save(role);
        RoleCache.addRole(role);
        if (!CollectionUtils.isEmpty(permissions)) {
            PermissionCache.pushPermissions(role.getId(), Sets.newHashSet(permissions));
        }
        return role.getId();
    }

    private void validateSaveRoleBo(SaveRoleBo saveRoleBo, Boolean isSaveMethod) {
        if (!isSaveMethod && saveRoleBo.getId() == null) {
            throw new BizException("没有指定需要修改角色ID");
        }
        if (StringUtils.isBlank(saveRoleBo.getName())) {
            throw new BizException("角色名不能为空");
        }
        Role role = RoleCache.getRoleByName(saveRoleBo.getName());
        if (role != null && (isSaveMethod || !isSaveMethod && !role.getId().equals(saveRoleBo.getId()))) {
            throw new BizException("角色名已存在");
        }
    }
}
