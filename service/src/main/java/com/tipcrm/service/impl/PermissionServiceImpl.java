package com.tipcrm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.tipcrm.bo.PermissionBo;
import com.tipcrm.bo.PermissionGroupBo;
import com.tipcrm.bo.RoleBo;
import com.tipcrm.cache.PermissionCache;
import com.tipcrm.dao.entity.PermissionGroup;
import com.tipcrm.dao.entity.Role;
import com.tipcrm.dao.entity.RolePermission;
import com.tipcrm.dao.entity.User;
import com.tipcrm.dao.repository.PermissionGroupRepository;
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

    @Override
    public Set<String> getPermissionValueListByUserId(Integer userId) {
        Set<PermissionBo> permissions = PermissionCache.getPermissions(userId);
        if (CollectionUtils.isEmpty(permissions)) {
            User user = userRepository.findOne(userId);
            for (Role role : user.getRoles()) {
                for (RolePermission rolePermission : role.getRolePermissions()) {
                    permissions.add(PermissionBo.toPermissionBo(rolePermission.getPermission()));
                }
            }
            PermissionCache.addOrUpdatePermissions(userId, permissions);
        }
        return permissions.stream().map(permission -> permission.getName()).collect(Collectors.toSet());
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
        Set<RoleBo> myRoles = roleService.getRolesByUserId(userId);
        if (!CollectionUtils.isEmpty(myRoles)) {
            Set<PermissionBo> myPermissions = PermissionCache.getPermissions(myRoles.stream().map(role -> role.getId()).collect(Collectors.toList()));
            for (PermissionBo myPermission: myPermissions) {
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

    private List<PermissionBo> flatPermissionGroup(List<PermissionGroupBo> groups) {
        List<PermissionBo> permissionBos = new ArrayList<>();
        for (PermissionGroupBo group: groups) {
            permissionBos.addAll(group.getPermissions());
        }
        return permissionBos;
    }
}
