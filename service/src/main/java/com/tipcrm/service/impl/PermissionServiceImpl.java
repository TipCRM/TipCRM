package com.tipcrm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;
import com.tipcrm.bo.PermissionBo;
import com.tipcrm.bo.PermissionGroupBo;
import com.tipcrm.bo.RoleBasicBo;
import com.tipcrm.cache.PermissionCache;
import com.tipcrm.dao.entity.Permission;
import com.tipcrm.dao.entity.PermissionGroup;
import com.tipcrm.dao.entity.Role;
import com.tipcrm.dao.entity.RolePermission;
import com.tipcrm.dao.entity.User;
import com.tipcrm.dao.repository.PermissionGroupRepository;
import com.tipcrm.dao.repository.PermissionRepository;
import com.tipcrm.dao.repository.RolePermissionRepository;
import com.tipcrm.dao.repository.RoleRepository;
import com.tipcrm.dao.repository.UserRepository;
import com.tipcrm.exception.BizException;
import com.tipcrm.service.PermissionService;
import com.tipcrm.service.RoleService;
import com.tipcrm.service.WebContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionGroupRepository permissionGroupRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private WebContext webContext;

    @Override
    public Set<String> getPermissionValueListByUserId(Integer userId) {
        List<PermissionGroupBo> groups = getPermissionsByUserId(userId);
        Set<String> permissions = new HashSet<>();
        if (!CollectionUtils.isEmpty(groups)) {
            for (PermissionGroupBo group : groups) {
                permissions.addAll(group.getPermissions().stream().filter(p -> p.getChecked()).map(p -> p.getValue()).collect(Collectors.toList()));
            }
        }
        return permissions;
    }

    @Override
    public List<PermissionGroupBo> getAllGroup() {
        List<PermissionGroup> groups = permissionGroupRepository.findAll();
        return PermissionGroupBo.toPermissionGroupBos(groups);
    }

    @Override
    public List<PermissionGroupBo> getPermissionsByUserId(Integer userId) {
        List<PermissionGroupBo> groupBos = PermissionCache.getAllPermissionGroups();
        List<PermissionBo> allPermission = flatPermissionGroup(groupBos);
        Set<RoleBasicBo> myRoles = roleService.getRolesByUserId(userId);
        if (!CollectionUtils.isEmpty(myRoles)) {
            Set<PermissionBo> myPermissions = PermissionCache.getPermissions(myRoles.stream().map(role -> role.getId()).collect(Collectors.toList()));
            for (PermissionBo myPermission : myPermissions) {
                for (PermissionBo permission : allPermission) {
                    if (myPermission.getId().equals(permission.getId())) {
                        permission.setChecked(true);
                        break;
                    }
                }
            }
        }
        return groupBos;
    }

    @Override
    public List<PermissionGroupBo> getPermissionsByRoleId(Integer roleId) {
        List<PermissionGroupBo> groupBos = PermissionCache.getAllPermissionGroups();
        List<PermissionBo> allPermission = flatPermissionGroup(groupBos);
        Set<PermissionBo> myPermissions = PermissionCache.getPermissions(roleId);
        if (myPermissions == null) {
            throw new BizException("角色不存在");
        }
        if (!CollectionUtils.isEmpty(myPermissions)) {
            for (PermissionBo myPermission : myPermissions) {
                for (PermissionBo permission : allPermission) {
                    if (myPermission.getId().equals(permission.getId())) {
                        permission.setChecked(true);
                        break;
                    }
                }
            }
        }
        return groupBos;
    }

    @Override
    public void updateRolePermissions(Integer roleId, Set<Integer> permissionIds) {
        List<PermissionGroupBo> permissionGroupBos = getPermissionsByRoleId(roleId);
        List<PermissionBo> permissionBos = flatPermissionGroup(permissionGroupBos);
        Set<Integer> existIds = new HashSet<>();
        if (!CollectionUtils.isEmpty(permissionBos)) {
            existIds = permissionBos.stream().filter(permissionBo -> permissionBo.getChecked()).map(permissionBo -> permissionBo.getId()).collect(
                Collectors.toSet());
        }
        Set<Integer> needRemove = new HashSet<>();
        Set<Integer> needAdd = new HashSet<>();
        for (Integer exist : existIds) {
            if (!permissionIds.contains(exist)) {
                needRemove.add(exist);
            }
        }
        for (Integer permission : permissionIds) {
            if (!existIds.contains(permission)) {
                needAdd.add(permission);
            }
        }
        if (!CollectionUtils.isEmpty(needRemove)) {
            rolePermissionRepository.deleteByRoleIdAndPermissionId(roleId, needRemove);
            PermissionCache.popPermissions(roleId, needRemove);
        }
        if (!CollectionUtils.isEmpty(needAdd)) {
            Role role = roleRepository.findOne(roleId);

            List<Permission> permissions = permissionRepository.findByIdIn(needAdd);
            User user = webContext.getCurrentUser();
            Date date = new Date();
            List<RolePermission> rolePermissionList = new ArrayList<>();
            for (Permission permission : permissions) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRole(role);
                rolePermission.setPermission(permission);
                rolePermission.setDeletable(true);
                rolePermission.setEntryUser(user);
                rolePermission.setEntryTime(date);
                rolePermissionList.add(rolePermission);
            }
            rolePermissionRepository.save(rolePermissionList);
            PermissionCache.pushPermissions(roleId, Sets.newHashSet(PermissionBo.toPermissionBos(permissions)));
        }
    }

    private List<PermissionBo> flatPermissionGroup(List<PermissionGroupBo> groups) {
        List<PermissionBo> permissionBos = new ArrayList<>();
        for (PermissionGroupBo group : groups) {
            permissionBos.addAll(group.getPermissions());
        }
        return permissionBos;
    }
}
