package com.tipcrm.service.impl;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.tipcrm.bo.Constants;
import com.tipcrm.bo.LoginBo;
import com.tipcrm.bo.RegistBo;
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
    public String regist(RegistBo registBo) throws Exception {
        Configuration registable = configurationRepository.findByKey(ConfigurationItems.REGISTABLE.name());
        if (!Boolean.valueOf(registable.getValue())) {
            throw new Exception("管理员没有开放注册通道");
        }
        validateRegistUser(registBo);
        User user = new User();
        user.setEmail(registBo.getEmail());
        user.setUserName(registBo.getUsername());
        user.setStatus(1);
        user.setHireTime(new Date());
        userRepository.save(user);
        Security security = new Security();
        security.setUserId(user.getId());
        String salt = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        String securityPwd = new SimpleHash("MD5", registBo.getPassword(), salt, Constants.HASH_ITERATIONS).toHex();
        security.setSalt(salt);
        security.setPassword(securityPwd);
        securityRepository.save(security);
        return registBo.getEmail();
    }

    @Override
    public void login(LoginBo loginBo) throws Exception {
        validateLoginInfo(loginBo);
        UsernamePasswordToken token = new UsernamePasswordToken(loginBo.getLoginKey(), loginBo.getPassword());
        SecurityUtils.getSubject().login(token);
    }

    private void validateRegistUser(RegistBo registBo) throws Exception {
        if (StringUtils.isBlank(registBo.getEmail())) {
            throw new Exception("邮箱不能为空");
        }
        if (StringUtils.isBlank(registBo.getPassword())) {
            throw new Exception("密码不能为空");
        }
        if (StringUtils.isBlank(registBo.getUsername())) {
            throw new Exception("姓名不能为空");
        }
        if (!EmailValidator.getInstance().isValid(registBo.getEmail())) {
            throw new Exception("邮箱格式不正确");
        }
        if (registBo.getPassword().length() < 6) {
            throw new Exception("密码不能小于6位");
        }
        // 1. validate user exist
        User user = userRepository.findByEmailOrPhoneNo(registBo.getEmail());
        if (user != null) {
            throw new Exception("用户已存在");
        }
    }

    private void validateLoginInfo(LoginBo loginBo) throws Exception {
        if (StringUtils.isBlank(loginBo.getLoginKey())) {
            throw new Exception("登陆名不能为空");
        }
        if (StringUtils.isBlank(loginBo.getPassword())) {
            throw new Exception("密码不能为空");
        }
    }
}
