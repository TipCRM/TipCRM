package com.tipcrm.service.impl;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.tipcrm.bo.Constants;
import com.tipcrm.dao.entity.Configuration;
import com.tipcrm.dao.entity.Permission;
import com.tipcrm.dao.entity.Role;
import com.tipcrm.dao.entity.Security;
import com.tipcrm.dao.entity.User;
import com.tipcrm.dao.enums.ConfigurationItems;
import com.tipcrm.dao.repository.ConfigurationRepository;
import com.tipcrm.dao.repository.SecurityRepository;
import com.tipcrm.dao.repository.UserRepository;
import com.tipcrm.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
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

    @Autowired
    private ConfigurationRepository configurationRepository;

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
    public String regist(String email, String password, String username, Boolean isManager) throws Exception {
        Configuration registable = configurationRepository.findByKey(ConfigurationItems.REGISTABLE.name());
        if (!Boolean.valueOf(registable.getValue())) {
            throw new Exception("管理员没有开放注册通道");
        }
        validateRegistUser(email, password, username, isManager);
        User user = new User();
        user.setEmail(email);
        user.setUserName(username);
        user.setStatus(1);
        user.setHireTime(new Date());
        userRepository.save(user);
        Security security = new Security();
        security.setUserId(user.getId());
        String salt = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        String securityPwd = new SimpleHash("MD5", password, salt, Constants.HASH_ITERATIONS).toHex();
        security.setSalt(salt);
        security.setPassword(securityPwd);
        securityRepository.save(security);
        return email;
    }

    @Override
    public void login(String loginKey, String password) throws Exception {
        UsernamePasswordToken token = new UsernamePasswordToken(loginKey, password);
        SecurityUtils.getSubject().login(token);
    }

    private void validateRegistUser(String email, String password, String username, Boolean isManager) throws Exception {
        if (StringUtils.isBlank(email)) {
            throw new Exception("邮箱不能为空");
        }
        if (StringUtils.isBlank(password)) {
            throw new Exception("密码不能为空");
        }
        if (StringUtils.isBlank(username)) {
            throw new Exception("姓名不能为空");
        }
        if (!EmailValidator.getInstance().isValid(email)) {
            throw new Exception("邮箱格式不正确");
        }
        if (password.length() < 6) {
            throw new Exception("密码不能小于6位");
        }
        // 1. validate user exist
        User user = userRepository.findByEmailOrPhoneNo(email);
        if (user != null) {
            throw new Exception("用户已存在");
        }
    }
}
