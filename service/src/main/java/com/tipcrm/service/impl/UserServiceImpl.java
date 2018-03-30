package com.tipcrm.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.tipcrm.bo.CreateUserBo;
import com.tipcrm.bo.LoginBo;
import com.tipcrm.bo.RegistUserBo;
import com.tipcrm.bo.UserBo;
import com.tipcrm.constant.ConfigurationItems;
import com.tipcrm.constant.Constants;
import com.tipcrm.constant.Levels;
import com.tipcrm.constant.ListBoxCategory;
import com.tipcrm.constant.Roles;
import com.tipcrm.constant.UserStatus;
import com.tipcrm.dao.entity.Department;
import com.tipcrm.dao.entity.Level;
import com.tipcrm.dao.entity.ListBox;
import com.tipcrm.dao.entity.Role;
import com.tipcrm.dao.entity.Security;
import com.tipcrm.dao.entity.User;
import com.tipcrm.dao.repository.DepartmentRepository;
import com.tipcrm.dao.repository.LevelRepository;
import com.tipcrm.dao.repository.RoleRepository;
import com.tipcrm.dao.repository.SecurityRepository;
import com.tipcrm.dao.repository.UserRepository;
import com.tipcrm.exception.AccountException;
import com.tipcrm.exception.BizException;
import com.tipcrm.service.ConfigurationService;
import com.tipcrm.service.ListBoxService;
import com.tipcrm.service.MailService;
import com.tipcrm.service.RoleService;
import com.tipcrm.service.UserService;
import com.tipcrm.service.WebContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityRepository securityRepository;

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private ListBoxService listBoxService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private WebContext webContext;

    @Autowired
    private MailService mailService;

    @Autowired
    private RoleService roleService;

    @Override
    public String regist(RegistUserBo registUserBo) {
        String registable = configurationService.get(ConfigurationItems.REGISTABLE.name());
        if (!Boolean.valueOf(registable)) {
            throw new BizException("管理员没有开放注册通道");
        }

        ListBox userStatusActive = listBoxService.findByCategoryAndName(ListBoxCategory.USER_STATUS.name(), UserStatus.ACTIVE.name());
        List<User> users = userRepository.findByUserName(Constants.User.SYSTEM);
        if (CollectionUtils.isEmpty(users)) {
            throw new BizException("系统用户丢失，请联系运维人员修复数据库");
        }
        User hirer = users.get(0);
        Department department = null;
        if (registUserBo.getDepartmentId() != null) {
            department = departmentRepository.findOne(registUserBo.getDepartmentId());
        }
        Level level = levelRepository.findByName(Levels.NEW_USER.name());
        Role role = null;
        if (registUserBo.getTopManager()) {
            role = roleRepository.findByName(Roles.GENERAL_MANAGER.name());
        } else {
            role = roleRepository.findByName(Roles.NORMAL.name());
        }
        validateRegistUser(registUserBo);
        User user = new User();
        user.setEmail(registUserBo.getEmail());
        user.setUserName(registUserBo.getUsername());
        user.setStatus(userStatusActive);
        user.setHire(hirer);
        user.setHireTime(new Date());
        user.setDepartment(department);
        user.setLevel(level);
        user.setRoles(Lists.newArrayList(role));
        user.setPaymentPercent(level.getDefaultPaymentPercent());
        userRepository.save(user);
        saveSecurity(user.getId(), registUserBo.getPassword());
        return registUserBo.getEmail();
    }

    @Override
    public String saveUser(CreateUserBo createUserBo) {
        ListBox userStatusActive = listBoxService.findByCategoryAndName(ListBoxCategory.USER_STATUS.name(), UserStatus.ACTIVE.name());
        User hirer = webContext.getCurrentUser();
        Department department = null;
        if (createUserBo.getDepartmentId() != null) {
            department = departmentRepository.findOne(createUserBo.getDepartmentId());
        }
        Level level = levelRepository.findByName(Levels.NEW_USER.name());
        Role role = roleRepository.findByName(Roles.NORMAL.name());
        validateSaveUserBo(createUserBo);
        User user = new User();
        user.setEmail(createUserBo.getEmail());
        user.setUserName(createUserBo.getUsername());
        user.setStatus(userStatusActive);
        user.setHire(hirer);
        user.setHireTime(new Date());
        user.setDepartment(department);
        user.setLevel(level);
        user.setRoles(Lists.newArrayList(role));
        user.setPaymentPercent(level.getDefaultPaymentPercent());
        userRepository.save(user);
        String randomPwd = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
        saveSecurity(user.getId(), randomPwd);
        mailService.sendSimpleEmail(createUserBo.getEmail(), "注册通知", "管理员已为您分配帐号，初始密码是" + randomPwd + "，请尽快登陆系统修改密码。");
        return createUserBo.getEmail();
    }

    private void saveSecurity(Integer userId, String password) {
        Security security = new Security();
        security.setUserId(userId);
        String salt = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        String securityPwd = new SimpleHash("MD5", password, salt, Constants.HASH_ITERATIONS).toHex();
        security.setSalt(salt);
        security.setPassword(securityPwd);
        securityRepository.save(security);
    }

    @Override
    public void login(LoginBo loginBo) {
        validateLoginInfo(loginBo);
        UsernamePasswordToken token = new UsernamePasswordToken(loginBo.getLoginKey(), loginBo.getPassword());
        try {
            SecurityUtils.getSubject().login(token);
        } catch (IncorrectCredentialsException e) {
            throw new AccountException("帐号或密码错误");
        } catch (AuthenticationException e) {
            throw new AccountException(e.getMessage());
        }
    }

    @Override
    public UserBo getUserByUserId(Integer userId) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            throw new BizException("用户不存在");
        }
        UserBo userBo = convertToUserBo(user);
        return userBo;
    }

    @Override
    public Boolean isUserExist(Integer userId) {
        if (userId == null) {
            throw new BizException("用户Id不能为空");
        }
        User user = userRepository.findOne(userId);
        return user != null;
    }

    @Override
    public Boolean isGeneralManager(Integer userId) {
        Set<Role> roles = roleService.getRolesByUserId(userId);
        for (Role roleBo : roles) {
            if (roleBo.getName().equals(Roles.GENERAL_MANAGER.name())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<User> findGeneralManager() {
        return userRepository.findByRole(Roles.GENERAL_MANAGER.name());
    }

    @Override
    public User findSystemUser() {
        User user = userRepository.findByUserName(Constants.User.SYSTEM).get(0);
        return user;
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
            for (Role role : roles) {
                roleStr.add(role.getDisplayName());
            }
        }
        userBo.setRoles(roleStr);
        userBo.setStatus(user.getStatus().getDisplayName());
        userBo.setUserName(user.getUserName());
        return userBo;
    }

    private void validateRegistUser(RegistUserBo registUserBo) {
        if (StringUtils.isBlank(registUserBo.getEmail())) {
            throw new BizException("邮箱不能为空");
        }
        if (!EmailValidator.getInstance().isValid(registUserBo.getEmail())) {
            throw new BizException("邮箱格式不正确");
        }
        if (StringUtils.isBlank(registUserBo.getPassword())) {
            throw new BizException("密码不能为空");
        }
        if (registUserBo.getPassword().length() < 6) {
            throw new BizException("密码不能小于6位");
        }
        if (StringUtils.isBlank(registUserBo.getUsername())) {
            throw new BizException("姓名不能为空");
        }
        if (Constants.User.SYSTEM.equals(registUserBo.getUsername())) {
            throw new BizException("非法用户名");
        }
        // 1. validate user exist
        User user = userRepository.findByEmailOrPhoneNo(registUserBo.getEmail());
        if (user != null) {
            throw new BizException("用户已存在");
        }
    }

    private void validateSaveUserBo(CreateUserBo createUserBo) {
        if (StringUtils.isBlank(createUserBo.getEmail())) {
            throw new BizException("邮箱不能为空");
        }
        if (!EmailValidator.getInstance().isValid(createUserBo.getEmail())) {
            throw new BizException("邮箱格式不正确");
        }
        if (StringUtils.isBlank(createUserBo.getUsername())) {
            throw new BizException("姓名不能为空");
        }
        if (Constants.User.SYSTEM.equals(createUserBo.getUsername())) {
            throw new BizException("非法用户名");
        }
        // 1. validate user exist
        User user = userRepository.findByEmailOrPhoneNo(createUserBo.getEmail());
        if (user != null) {
            throw new BizException("用户已存在");
        }
    }

    private void validateLoginInfo(LoginBo loginBo) {
        if (StringUtils.isBlank(loginBo.getLoginKey())) {
            throw new AccountException("登陆名不能为空");
        }
        if (StringUtils.isBlank(loginBo.getPassword())) {
            throw new AccountException("密码不能为空");
        }
    }

    @Override
    public void logout() {
        SecurityUtils.getSubject().logout();
    }
}
