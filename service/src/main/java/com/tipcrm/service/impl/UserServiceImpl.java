package com.tipcrm.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.tipcrm.bo.Constants;
import com.tipcrm.dao.entity.Permission;
import com.tipcrm.dao.entity.Role;
import com.tipcrm.dao.entity.Security;
import com.tipcrm.dao.entity.User;
import com.tipcrm.dao.repository.SecurityRepository;
import com.tipcrm.dao.repository.UserRepository;
import com.tipcrm.service.UserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityRepository securityRepository;

    @Override
    public Set<String> getRoleListByUserId(Integer userId) {
        Set<String> roles = new HashSet<String>();
        User user = userRepository.findOne(userId);
        for (Role role : user.getRoles()) {
            roles.add(role.getName());
        }
        return roles;
    }

    @Override
    public Set<String> getPermissionValueListByUserId(Integer userId) {
        Set<String> permissions = new HashSet<String>();
        User user = userRepository.findOne(userId);
        for (Role role : user.getRoles()) {
            for (Permission permission : role.getPermissions()) {
                permissions.add(permission.getValue());
            }
        }
        return permissions;
    }

    @Override
    public String regist(String username, String password, String email, Boolean isManager) throws Exception{
        validateRegistUser(username, isManager);
        User user = new User();
        user.setUserName(username);
        user.setEmail(email);
        user.setStatus(1);
        user.setHireId(0);
        user.setHireTime(new Date());
        user.setManagerId(0);
        user.setDepartmentId(0);
        user.setLevelId(1);
        userRepository.save(user);
        Security security = new Security();
        security.setUserId(user.getId());
        String salt = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5);
        String securityPwd = new SimpleHash("MD5", password, salt, Constants.HASH_ITERATIONS).toHex();
        security.setSalt(salt);
        security.setPassword(password);
        securityRepository.save(security);
        return username;
    }

    public void validateRegistUser(String username, Boolean isManager) throws Exception{
        // 1. validate user exist
        User user = userRepository.findByUserName(username);
        if (user != null) {
            throw new Exception("用户已存在");
        }
    }
}
