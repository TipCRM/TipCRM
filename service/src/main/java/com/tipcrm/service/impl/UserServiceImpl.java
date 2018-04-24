package com.tipcrm.service.impl;

import com.google.common.collect.Sets;
import com.tipcrm.bo.CreateUserBo;
import com.tipcrm.bo.DismissBo;
import com.tipcrm.bo.LoginBo;
import com.tipcrm.bo.QueryCriteriaBo;
import com.tipcrm.bo.QueryRequestBo;
import com.tipcrm.bo.QueryResultBo;
import com.tipcrm.bo.QuerySortBo;
import com.tipcrm.bo.UpdateUserBo;
import com.tipcrm.bo.UserBasicBo;
import com.tipcrm.bo.UserBo;
import com.tipcrm.bo.UserDepartmentAssignBo;
import com.tipcrm.bo.UserExtBo;
import com.tipcrm.bo.UserLevelAssignBo;
import com.tipcrm.constant.AttachmentLocation;
import com.tipcrm.constant.AttachmentType;
import com.tipcrm.constant.Constants;
import com.tipcrm.constant.Levels;
import com.tipcrm.constant.ListBoxCategory;
import com.tipcrm.constant.Roles;
import com.tipcrm.constant.UserStatus;
import com.tipcrm.dao.entity.Attachment;
import com.tipcrm.dao.entity.Department;
import com.tipcrm.dao.entity.Level;
import com.tipcrm.dao.entity.ListBox;
import com.tipcrm.dao.entity.Role;
import com.tipcrm.dao.entity.Security;
import com.tipcrm.dao.entity.User;
import com.tipcrm.dao.entity.UserRole;
import com.tipcrm.dao.repository.DepartmentRepository;
import com.tipcrm.dao.repository.LevelRepository;
import com.tipcrm.dao.repository.RoleRepository;
import com.tipcrm.dao.repository.SecurityRepository;
import com.tipcrm.dao.repository.UserRepository;
import com.tipcrm.dao.repository.UserRoleRepository;
import com.tipcrm.exception.AccountException;
import com.tipcrm.exception.BizException;
import com.tipcrm.exception.QueryException;
import com.tipcrm.service.AttachmentService;
import com.tipcrm.service.ConfigurationService;
import com.tipcrm.service.ListBoxService;
import com.tipcrm.service.MailService;
import com.tipcrm.service.RoleService;
import com.tipcrm.service.UserService;
import com.tipcrm.service.WebContext;
import com.tipcrm.util.IDCardValidator;
import com.tipcrm.util.MessageUtil;
import com.tipcrm.util.ValidateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private AttachmentService attachmentService;

    @Override
    public Integer saveUser(CreateUserBo createUserBo) {
        ListBox userStatusActive = listBoxService.findByCategoryAndName(ListBoxCategory.USER_STATUS.name(), UserStatus.ACTIVE.name());
        User hirer = webContext.getCurrentUser();
        Department department = null;
        if (createUserBo.getDepartmentId() != null) {
            department = departmentRepository.findOne(createUserBo.getDepartmentId());
        }
        Level level = levelRepository.findByName(Levels.NEW_USER.getValue());
        Role role = roleRepository.findByName(Roles.NORMAL.getValue());
        validateSaveUserBo(createUserBo);
        Integer workNo = configurationService.generateNewWorkNo();
        User user = new User();
        user.setWorkNo(workNo);
        user.setEmail(createUserBo.getEmail());
        user.setUserName(createUserBo.getName());
        user.setStatus(userStatusActive);
        user.setHire(hirer);
        user.setHireTime(new Date());
        user.setDepartment(department);
        user.setLevel(level);
        user.setAvatar(attachmentService.findDefaultAvatar());
        user.setPaymentPercent(level.getDefaultPaymentPercent());
        userRepository.save(user);
        UserRole userRole = new UserRole();
        userRole.setRole(role);
        userRole.setUser(user);
        userRoleRepository.save(userRole);
        String randomPwd = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
        Security security = generateSecurity(user.getId(), randomPwd);
        securityRepository.save(security);
        mailService.sendSimpleEmail(createUserBo.getEmail(), "注册通知",
                MessageUtil.getMessage(Constants.Email.ADD_USER_CONTENT, workNo, user.getEmail(), randomPwd));
        return user.getWorkNo();
    }

    private Security generateSecurity(Integer userId, String password) {
        Security security = new Security();
        security.setUserId(userId);
        String salt = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        String securityPwd = new SimpleHash("MD5", password, salt, Constants.HASH_ITERATIONS).toHex();
        security.setSalt(salt);
        security.setPassword(securityPwd);
        return security;
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
    public UserExtBo getUserByUserId(Integer userId) {
        if (userId < 0) {
            throw new BizException("用户不存在");
        }
        User user = userRepository.findOne(userId);
        if (user == null) {
            throw new BizException("用户不存在");
        }
        UserExtBo userExtBo = convertToUserExtBo(user);
        return userExtBo;
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
        List<Role> roles = roleService.getRolesByUserId(userId);
        for (Role roleBo : roles) {
            if (roleBo.getName().equals(Roles.GENERAL_MANAGER.getValue())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<User> findGeneralManager() {
        return userRepository.findByRole(Roles.GENERAL_MANAGER.getValue());
    }

    @Override
    public User findSystemUser() {
        User user = userRepository.findByUserName(Constants.User.SYSTEM).get(0);
        return user;
    }

    private UserExtBo convertToUserExtBo(User user) {
        UserExtBo userExtBo = new UserExtBo();
        Attachment avatar = user.getAvatar();
        String path = avatar.getId();
        if (StringUtils.isNotBlank(avatar.getExt())) {
            path += "." + avatar.getExt();
        }
        userExtBo.setId(user.getId());
        userExtBo.setWorkNo(user.getWorkNo());
        userExtBo.setAvatar(path);
        userExtBo.setMotto(user.getMotto());
        userExtBo.setBirthday(user.getBirthday());
        Department department = user.getDepartment();
        if (department != null) {
            userExtBo.setDepartmentId(department.getId());
            userExtBo.setDepartment(department.getName());
            User manager = department.getManager();
            if (manager != null) {
                userExtBo.setManager(manager.getUserName());
                userExtBo.setIsDepartmentManager(manager.getId().equals(user.getId()));
            }
        }
        userExtBo.setEmail(user.getEmail());
        User hirer = user.getHire();
        if (hirer != null) {
            userExtBo.setHirer(hirer.getUserName());
        }
        userExtBo.setHireTime(user.getHireTime());
        userExtBo.setIdCard(user.getIdCard());
        Level level = user.getLevel();
        if (level != null) {
            userExtBo.setLevelId(level.getId());
            userExtBo.setLevel(level.getName());
        }
        userExtBo.setPhoneNo(user.getPhoneNo());
        List<Role> roles = roleService.getRolesByUserId(user.getId());
        List<String> roleStr = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(roles)) {
            for (Role role : roles) {
                roleStr.add(role.getName());
            }
        }
        userExtBo.setRoles(roleStr);
        userExtBo.setStatus(user.getStatus().getDisplayName());
        userExtBo.setName(user.getUserName());
        userExtBo.setPaymentPercentage(user.getPaymentPercent());
        if (user.getDismissUser() != null) {
            userExtBo.setDismissUser(user.getDismissUser().getUserName());
            userExtBo.setDismissDate(user.getDismissTime());
            userExtBo.setDismissReason(user.getDismissReason());
        }
        return userExtBo;
    }

    private void validateSaveUserBo(CreateUserBo createUserBo) {
        if (StringUtils.isBlank(createUserBo.getEmail())) {
            throw new BizException("邮箱不能为空");
        }
        if (!EmailValidator.getInstance().isValid(createUserBo.getEmail())) {
            throw new BizException("邮箱格式不正确");
        }
        if (StringUtils.isBlank(createUserBo.getName())) {
            throw new BizException("姓名不能为空");
        }
        if (Constants.User.SYSTEM.equals(createUserBo.getName())) {
            throw new BizException("非法用户名");
        }
        // 1. validate user exist
        User user = userRepository.findByEmailOrWorkNo(createUserBo.getEmail());
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

    @Override
    public List<UserBasicBo> findByName(String userName, Boolean includeDismiss) {
        if (StringUtils.isBlank(userName)) {
            throw new BizException("用户名不能为空");
        }
        userName = "%" + userName + "%";
        List<User> users;
        if (includeDismiss) {
            users = userRepository.findByNameIncludeDismiss(userName);
        } else {
            users = userRepository.findByNameWithoutDismiss(userName);
        }
        return UserBasicBo.convertToUserBasicBos(users);
    }

    @Override
    public QueryResultBo<UserBo> queryUser(QueryRequestBo queryRequestBo) {
        List<QueryCriteriaBo> queryCriteriaBos = queryRequestBo.getCriteria();
        if (!CollectionUtils.isEmpty(queryCriteriaBos)) {
            Set<String> fieldSet = Sets.newHashSet();
            for (QueryCriteriaBo queryCriteriaBo : queryCriteriaBos) {
                fieldSet.add(queryCriteriaBo.getFieldName());
            }
            if (fieldSet.size() != queryCriteriaBos.size()) {
                throw new BizException("查询字段有重复");
            }
        }
        try {
            QuerySortBo querySortBo = queryRequestBo.getSort();
            PageRequest page;
            if (querySortBo == null) {
                page = new PageRequest(queryRequestBo.getPage() - 1, queryRequestBo.getSize());
            } else {
                page = new PageRequest(queryRequestBo.getPage() - 1, queryRequestBo.getSize(),
                        new Sort(querySortBo.getDirection(),
                                Constants.SortFieldName.User.fieldMap.get(querySortBo.getFieldName())));
            }
            Specification<User> specification = new UserSpecification(queryRequestBo);
            Page<User> users = userRepository.findAll(specification, page);
            List<UserBo> customerBos = convertToUserBos(users.getContent());
            QueryResultBo<UserBo> queryResultBo = new QueryResultBo<>(customerBos, queryRequestBo.getPage(), queryRequestBo.getSize(),
                    users.getTotalElements(), users.getTotalPages());
            return queryResultBo;
        } catch (Exception e) {
            throw new QueryException("查询条件错误", e);
        }
    }

    public UserBo convertToUserBo(User user) {
        UserBo userBo = new UserBo();
        userBo.setId(user.getId());
        userBo.setWorkNo(user.getWorkNo());
        userBo.setName(user.getUserName());
        userBo.setEmail(user.getEmail());
        userBo.setPhoneNo(user.getPhoneNo());
        userBo.setMotto(user.getMotto());
        userBo.setStatus(user.getStatus().getName());
        Department department = user.getDepartment();
        if (department != null) {
            userBo.setDepartmentId(department.getId());
            userBo.setDepartment(department.getName());
            User manager = department.getManager();
            if (manager != null) {
                userBo.setManager(manager.getUserName());
                if (manager != null) {
                    userBo.setManager(manager.getUserName());
                    userBo.setIsDepartmentManager(manager.getId().equals(user.getId()));
                }
            }
        }
        Level level = user.getLevel();
        if (level != null) {
            userBo.setLevelId(level.getId());
            userBo.setLevel(level.getName());
        }
        return userBo;
    }

    public List<UserBo> convertToUserBos(List<User> users) {
        List<UserBo> userBos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(users)) {
            for (User user : users) {
                userBos.add(convertToUserBo(user));
            }
        }
        return userBos;

    }

    @Override
    public void updateMe(UpdateUserBo updateUserBo) {
        validateUpdateUser(updateUserBo);
        User user = webContext.getCurrentUser();
        user.setUserName(updateUserBo.getUserName());
        user.setEmail(updateUserBo.getEmail());
        Date date = null;
        if (StringUtils.isNoneBlank(updateUserBo.getBirthday())) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(true);
                date = sdf.parse(updateUserBo.getBirthday());
            } catch (ParseException e) {
                throw new BizException("生日不正确");
            }
        }
        user.setBirthday(date);
        user.setIdCard(updateUserBo.getIdCard());
        user.setPhoneNo(updateUserBo.getPhoneNo());
        user.setMotto(updateUserBo.getMotto());
        User entryUser = webContext.getCurrentUser();
        Date entryTime = new Date();
        ListBox external = listBoxService.findByCategoryAndName(ListBoxCategory.ATTACHMENT_LOCATION.name(), AttachmentLocation.EXTERNAL.name());
        if (StringUtils.isNotBlank(updateUserBo.getAvatar())) {
            Attachment attachment;
            if (updateUserBo.getAvatarType().equals(external.getId())) {
                attachment = attachmentService.findByFileNameAndType(updateUserBo.getAvatar(), AttachmentType.AVATAR);
                user.setAvatar(attachment);
            } else {
                ListBox network = listBoxService.findByCategoryAndName(ListBoxCategory.ATTACHMENT_LOCATION.name(), AttachmentLocation.NETWORK.name());
                attachment = new Attachment();
                attachment.setLocationType(network);
                attachment.setPath(updateUserBo.getAvatar());
                Integer dotPos = updateUserBo.getAvatar().lastIndexOf(".");
                if (dotPos < 0) {
                    attachment.setId(updateUserBo.getAvatar());
                } else {
                    attachment.setId(UUID.randomUUID().toString().toLowerCase());
                    attachment.setExt(updateUserBo.getAvatar().substring(dotPos + 1));
                }
                attachment.setEntryTime(entryTime);
                attachment.setEntryUser(entryUser);
                attachment.setType(listBoxService.findByCategoryAndName(ListBoxCategory.ATTACHMENT_TYPE.name(), AttachmentType.AVATAR.name()));
            }
            user.setAvatar(attachment);
        }
        user.setUpdateTime(entryTime);
        user.setUpdateUser(entryUser);
        userRepository.save(user);
    }

    private void validateUpdateUser(UpdateUserBo updateUserBo) {
        if (StringUtils.isNotBlank(updateUserBo.getEmail())) {
            if (!ValidateUtils.isEmail(updateUserBo.getEmail())) {
                throw new BizException("邮箱格式不正确");
            }
            User user = userRepository.findByEmail(updateUserBo.getEmail());
            User currentUser = webContext.getCurrentUser();
            if (!user.getId().equals(currentUser.getId())) {
                throw new BizException("该邮箱已被绑定");
            }
        }

        ListBox external = listBoxService.findByCategoryAndName(ListBoxCategory.ATTACHMENT_LOCATION.name(), AttachmentLocation.EXTERNAL.name());
        if (updateUserBo.getAvatarType().equals(external.getId())) {
            Attachment attachment = attachmentService.findByFileNameAndType(updateUserBo.getAvatar(), AttachmentType.AVATAR);
            if (attachment == null) {
                throw new BizException("头像不存在，请上传");
            }
        } else {
            if (!ValidateUtils.isUrl(updateUserBo.getAvatar())) {
                throw new BizException("网络地址不正确");
            }
        }
        if (StringUtils.isBlank(updateUserBo.getUserName())) {
            throw new BizException("姓名不能为空");
        }
        if (StringUtils.isBlank(updateUserBo.getIdCard())) {
            throw new BizException("身份证号不能为空");
        } else if (!IDCardValidator.validate(updateUserBo.getIdCard())) {
            throw new BizException("身份证号不正确");
        }
    }

    static class UserSpecification implements Specification<User> {
        private QueryRequestBo queryRequestBo;

        public UserSpecification(QueryRequestBo queryRequestBo) {
            this.queryRequestBo = queryRequestBo;
        }

        @Override
        public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<Predicate>();
            if (!CollectionUtils.isEmpty(queryRequestBo.getCriteria())) {
                for (QueryCriteriaBo criteria : queryRequestBo.getCriteria()) {
                    Path path = null;
                    switch (criteria.getFieldName()) {
                        case Constants.QueryFieldName.User.USER_NAME:
                            path = root.get("userName");
                            String userName = (String) criteria.getValue();
                            if (StringUtils.isNotBlank(userName)) {
                                predicates.add(criteriaBuilder.like(path, "%" + userName + "%"));
                            }
                            break;
                        case Constants.QueryFieldName.User.STATUS:
                            path = root.get("status").get("id");
                            List statuses = (List) criteria.getValue();
                            if (!CollectionUtils.isEmpty(statuses)) {
                                predicates.add(path.in(statuses.toArray()));
                            }
                            break;
                        case Constants.QueryFieldName.User.DEPARTMENT_ID:
                            path = root.get("department").get("id");
                            List departmentId = (List) criteria.getValue();
                            if (!CollectionUtils.isEmpty(departmentId)) {
                                predicates.add(path.in(departmentId.toArray()));
                            }
                            break;
                        case Constants.QueryFieldName.User.LEVEL_ID:
                            path = root.get("level").get("id");
                            List levelIds = (List) criteria.getValue();
                            if (!CollectionUtils.isEmpty(levelIds)) {
                                predicates.add(path.in(levelIds.toArray()));
                            }
                            break;
                        case Constants.QueryFieldName.User.WORK_NO:
                            path = root.get("workNo");
                            Integer workNo = (Integer) criteria.getValue();
                            if (workNo != null) {
                                predicates.add(criteriaBuilder.like(path, "%" + workNo + "%"));
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
            Path path = root.get("id");
            predicates.add(criteriaBuilder.gt(path, 0));
            Predicate[] pre = new Predicate[predicates.size()];
            criteriaQuery.where(predicates.toArray(pre));
            return criteriaQuery.getRestriction();
        }
    }

    @Override
    public String generateChangePasswordValidationCode(String email) {
        String validationCode = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
        User user = webContext.getCurrentUser();
        if (StringUtils.isBlank(user.getEmail())) {
            throw new BizException("请先配置用户邮箱");
        }
        if (!user.getEmail().equals(email)) {
            throw new BizException("邮箱地址与帐户设置不匹配");
        }
        mailService.sendSimpleEmail(email, "修改密码", MessageUtil.getMessage(Constants.Email.CHANGE_PASSWORD_VALIDATION_CODE, validationCode));
        return validationCode;
    }

    @Override
    public void changePassword(String newPassword) {
        if (StringUtils.isBlank(newPassword)) {
            throw new BizException("新密码不能为空");
        }
        if (newPassword.length() < 6) {
            throw new BizException("新密码至少6位");
        }
        Security security = generateSecurity(webContext.getCurrentUserId(), newPassword);
        securityRepository.save(security);
    }

    @Override
    public void userDepartmentAssign(UserDepartmentAssignBo assignBo) {
        if (assignBo.getUserId() == null) {
            throw new BizException("用户不能为空");
        }
        if (assignBo.getDepartmentId() == null) {
            throw new BizException("部门不能为空");
        }
        User user = userRepository.findByIdWithoutDismiss(assignBo.getUserId());
        if (user == null) {
            throw new BizException("用户不存在或已离职");
        }
        Department department = departmentRepository.findByIdAndDeleteTimeIsNull(assignBo.getDepartmentId());
        if (department == null) {
            throw new BizException("部门不存在");
        }
        user.setDepartment(department);
        Department managed = departmentRepository.findByManagerIdAndDeleteTimeIsNull(assignBo.getUserId());
        if (managed != null) {
            managed.setManager(null);
            departmentRepository.save(managed);
        }
        userRepository.save(user);
    }

    @Override
    public void userLevelAssign(UserLevelAssignBo assignBo) {
        if (assignBo.getUserId() == null) {
            throw new BizException("用户不能为空");
        }
        if (assignBo.getLevelId() == null) {
            throw new BizException("等级不能为空");
        }
        BigDecimal payment = assignBo.getPaymentPercentage();
        User user = userRepository.findByIdWithoutDismiss(assignBo.getUserId());
        if (user == null) {
            throw new BizException("用户不存在或已离职");
        }
        Level level = levelRepository.findByIdAndDeleteTimeIsNull(assignBo.getLevelId());
        if (level == null) {
            throw new BizException("等级不存在");
        }
        user.setLevel(level);
        if (payment == null) {
            payment = level.getDefaultPaymentPercent();
        }
        user.setPaymentPercent(payment.setScale(2, BigDecimal.ROUND_HALF_DOWN));
        userRepository.save(user);
    }


    @Override
    public void dismiss(DismissBo dismissBo) {
        if (dismissBo.getUserId() == null) {
            throw new BizException("用户能不能空");
        }
        if (StringUtils.isBlank(dismissBo.getReason())) {
            throw new BizException("原因不能为空");
        }
        if (dismissBo.getReason().length() > 255) {
         throw new BizException("原因过长");
        }
        User user = userRepository.findByIdWithoutDismiss(dismissBo.getUserId());
        if (user == null) {
            throw new BizException("用户不存在或已离职");
        }

        User entryUser = webContext.getCurrentUser();
        ListBox dismiss = listBoxService.findByCategoryAndName(ListBoxCategory.USER_STATUS.name(), UserStatus.DISMISS.name());
        user.setStatus(dismiss);
        user.setDismissUser(entryUser);
        user.setDismissReason(dismissBo.getReason());
        user.setDismissTime(new Date());
        Department managed = departmentRepository.findByManagerIdAndDeleteTimeIsNull(dismissBo.getUserId());
        if (managed != null) {
            managed.setManager(null);
            departmentRepository.save(managed);
        }
        userRepository.save(user);
    }
}
