package com.tipcrm.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.tipcrm.bo.PermissionBo;
import com.tipcrm.bo.PermissionGroupBo;
import com.tipcrm.bo.RoleBasicBo;
import com.tipcrm.cache.PermissionCache;
import com.tipcrm.dao.entity.PermissionGroup;
import com.tipcrm.dao.entity.Role;
import com.tipcrm.dao.entity.RolePermission;
import com.tipcrm.dao.entity.User;
import com.tipcrm.dao.repository.PermissionGroupRepository;
import com.tipcrm.dao.repository.RoleRepository;
import com.tipcrm.dao.repository.UserRepository;
import com.tipcrm.service.PermissionService;
import com.tipcrm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionGroupRepository permissionGroupRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Set<String> getPermissionValueListByUserId(Integer userId) {
        List<PermissionGroupBo> groups = getPermissionsByUserId(userId);
        Set<String> permissions = new HashSet<>();
        if (!CollectionUtils.isEmpty(groups)) {
            for (PermissionGroupBo group : groups) {
                permissions.addAll(group.getPermissions().stream().filter(p ->p.getChecked()).map(p -> p.getValue()).collect(Collectors.toList()));
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
        for (PermissionBo myPermission : myPermissions) {
            for (PermissionBo permission : allPermission) {
                if (myPermission.getId().equals(permission.getId())) {
                    permission.setChecked(true);
                    break;
                }
            }
        }
        return groupBos;
    }

    private List<PermissionBo> flatPermissionGroup(List<PermissionGroupBo> groups) {
        List<PermissionBo> permissionBos = new ArrayList<>();
        for (PermissionGroupBo group : groups) {
            permissionBos.addAll(group.getPermissions());
        }
        return permissionBos;
    }
}
