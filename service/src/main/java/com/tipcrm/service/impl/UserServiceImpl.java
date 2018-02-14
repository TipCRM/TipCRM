package com.tipcrm.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.tipcrm.bo.UserBo;
import com.tipcrm.constant.Constants;
import com.tipcrm.bo.LoginBo;
import com.tipcrm.bo.RegistBo;
import com.tipcrm.constant.Levels;
import com.tipcrm.constant.ListBoxCategory;
import com.tipcrm.constant.Roles;
import com.tipcrm.constant.UserStatus;
import com.tipcrm.dao.entity.Configuration;
import com.tipcrm.dao.entity.Department;
import com.tipcrm.dao.entity.Level;
import com.tipcrm.dao.entity.ListBox;
import com.tipcrm.dao.entity.Role;
import com.tipcrm.dao.entity.Security;
import com.tipcrm.dao.entity.User;
import com.tipcrm.constant.ConfigurationItems;
import com.tipcrm.dao.repository.ConfigurationRepository;
import com.tipcrm.dao.repository.DepartmentRepository;
import com.tipcrm.dao.repository.LevelRepository;
import com.tipcrm.dao.repository.RoleRepository;
import com.tipcrm.dao.repository.SecurityRepository;
import com.tipcrm.dao.repository.UserRepository;
import com.tipcrm.exception.BizException;
import com.tipcrm.service.ListBoxService;
import com.tipcrm.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityRepository securityRepository;

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Autowired
    private ListBoxService listBoxService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @CachePut(value = "user")
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Cacheable(value = "user", key = "#userId")
    public User findOne(Integer userId) {
        logger.debug("进入查询");
        return userRepository.findOne(userId);
    }

    @Override
    public User findByEmailOrPhoneNo(String key) {
        logger.debug("通过Key查询");
        return userRepository.findByEmailOrPhoneNo(key);
    }

    @Override
    public String regist(RegistBo registBo) throws Exception {
        Configuration registable = configurationRepository.findByKey(ConfigurationItems.REGISTABLE.name());
        ListBox userStatusActive = listBoxService.findByCategoryAndName(ListBoxCategory.USER_STATUS.name(), UserStatus.ACTIVE.name());
        List<User> users = userRepository.findByUserName(Constants.User.SYSTEM);
        if (CollectionUtils.isEmpty(users)) {
            throw new Exception("系统用户丢失，请联系运维人员修复数据库");
        }
        User systemUser = users.get(0);
        if (!Boolean.valueOf(registable.getValue())) {
            throw new Exception("管理员没有开放注册通道");
        }
        Department department = null;
        if (registBo.getDepartmentId() != null) {
            department = departmentRepository.findOne(registBo.getDepartmentId());
        }
        Level level = levelRepository.findByName(Levels.NEW_USER.name());
        Role role = null;
        if (registBo.getTopManager()) {
            role = roleRepository.findByName(Roles.GENERAL_MANAGER.name());
        } else {
            role = roleRepository.findByName(Roles.NORMAL.name());
        }
        validateRegistUser(registBo);
        User user = new User();
        user.setEmail(registBo.getEmail());
        user.setUserName(registBo.getUsername());
        user.setStatus(userStatusActive);
        user.setHire(systemUser);
        user.setHireTime(new Date());
        user.setDepartment(department);
        user.setLevel(level);
        user.setRoles(Lists.newArrayList(role));
        user.setPaymentPercent(level.getDefaultPaymentPercent());
        save(user);
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

    @Override
    public UserBo getUserByUserId(Integer userId) throws Exception {
        System.out.println("内调用");
        User user = findOne(userId);
        if (user == null) {
            throw new BizException("用户不存在");
        }
        UserBo userBo = convertToUserBo(user);
        return userBo;
    }

    private UserBo convertToUserBo(User user) {
        UserBo userBo = new UserBo();
        userBo.setAvatar(user.getAvatar());
        userBo.setBirthday(user.getBirthday());
        Department department = user.getDepartment();
        if (department != null) {
            userBo.setDepartment(user.getDepartment().getName());
            User manager = department.getManager();
            if (manager != null) {
                userBo.setManager(manager.getUserName());
            }
        }
        userBo.setEmail(user.getEmail());
        User hirer = user.getHire();
        if (hirer != null && !Constants.User.SYSTEM.equals(hirer.getUserName())) {
            userBo.setHirer(hirer.getUserName());
        }
        userBo.setHireTime(user.getHireTime());
        userBo.setIdCard(user.getIdCard());
        Level level = user.getLevel();
        if (level != null) {
            userBo.setLevel(level.getDisplayName());
        }
        userBo.setPhoneNo(user.getPhoneNo());
        List<Role> roles = user.getRoles();
        List<String> roleStr = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(roles)) {
            for (Role role: roles) {
                roleStr.add(role.getDisplayName());
            }
        }
        userBo.setRoles(roleStr);
        userBo.setStatus(user.getStatus().getDisplayName());
        userBo.setUserName(user.getUserName());
        return userBo;
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
        if (Constants.User.SYSTEM.equals(registBo.getUsername())) {
            throw new Exception("非法用户名");
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
