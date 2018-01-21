package com.tipcrm.service.impl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tipcrm.dao.entity.Permission;
import com.tipcrm.dao.entity.Role;
import com.tipcrm.dao.entity.User;
import com.tipcrm.dao.repository.UserRepository;
import com.tipcrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public Set<String> getRoleListByUserId(Long userId) {
        Set<String> roles = new HashSet<String>();
        User user = userRepository.findOne(userId);
        for (Role role : user.getRoles()) {
            roles.add(role.getName());
        }
        return roles;
    }

    @Override
    public Set<String> getPermissionValueListByUserId(Long userId) {
        Set<String> permissions = new HashSet<String>();
        User user = userRepository.findOne(userId);
        for (Role role : user.getRoles()) {
            for (Permission permission : role.getPermissions()) {
                permissions.add(permission.getValue());
            }
        }
        return permissions;
    }
}
