package com.tipcrm.service.impl;

import java.util.Set;

import com.google.common.collect.Sets;
import com.tipcrm.cache.RoleCache;
import com.tipcrm.dao.entity.Role;
import com.tipcrm.dao.entity.User;
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

    @Override
    public Set<String> getRoleListByUserId(Integer userId) {
        Set<String> roles = RoleCache.getRoles(userId);
        if (!CollectionUtils.isEmpty(roles)) {
            return roles;
        }
        roles = Sets.newHashSet();
        User user = userRepository.findOne(userId);
        for (Role role : user.getRoles()) {
            roles.add(role.getName());
        }
        RoleCache.addOrUpdateRoles(userId, roles);
        return roles;
    }
}
