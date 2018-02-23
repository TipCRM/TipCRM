package com.tipcrm.service.impl;

import java.util.Set;

import com.google.common.collect.Sets;
import com.tipcrm.cache.PermissionCache;
import com.tipcrm.dao.entity.Role;
import com.tipcrm.dao.entity.RolePermission;
import com.tipcrm.dao.entity.User;
import com.tipcrm.dao.repository.UserRepository;
import com.tipcrm.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Set<String> getPermissionValueListByUserId(Integer userId) {
        Set<String> permissions = PermissionCache.getPermissions(userId);
        if (!CollectionUtils.isEmpty(permissions)){
            return permissions;
        }
        permissions = Sets.newHashSet();
        User user = userRepository.findOne(userId);
        for (Role role : user.getRoles()) {
            for (RolePermission rolePermission : role.getRolePermissions()) {
                permissions.add(rolePermission.getPermission().getValue());
            }
        }
        PermissionCache.addOrUpdatePermissions(userId, permissions);
        return permissions;
    }
}
