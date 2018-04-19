package com.tipcrm.service;
import java.util.List;
import java.util.stream.Collectors;

import com.tipcrm.constant.UserStatus;
import com.tipcrm.dao.entity.Role;
import com.tipcrm.dao.entity.Security;
import com.tipcrm.dao.entity.User;
import com.tipcrm.dao.repository.SecurityRepository;
import com.tipcrm.dao.repository.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class TipCRMRealm extends AuthorizingRealm {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityRepository securityRepository;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Integer userId = (Integer) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<Role> roles = roleService.getRolesByUserId(userId);
        if (!CollectionUtils.isEmpty(roles)) {
            info.setRoles(roles.stream().map(role -> role.getName()).collect(Collectors.toSet()));
        }
        info.setStringPermissions(permissionService.getPermissionValuesByUserId(userId));
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String loginKey = (String) token.getPrincipal();
        User user = userRepository.findByEmailOrWorkNo(loginKey);
        if (user == null) {
            throw new AuthenticationException("帐号或密码错误");
        }
        if (UserStatus.FROZEN.name().equals(user.getStatus().getName())) {
            throw new AuthenticationException("帐户被冻结");
        }
        Security security = securityRepository.findOne(user.getId());
        return new SimpleAuthenticationInfo(user.getId(), security.getPassword(), ByteSource.Util.bytes(security.getSalt()), getName());
    }
}
