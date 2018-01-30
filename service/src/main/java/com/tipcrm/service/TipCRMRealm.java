package com.tipcrm.service;
import com.tipcrm.dao.entity.Security;
import com.tipcrm.dao.entity.User;
import com.tipcrm.dao.repository.SecurityRepository;
import com.tipcrm.dao.repository.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class TipCRMRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityRepository securityRepository;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Integer userId = (Integer) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(userService.getRoleListByUserId(userId));
        info.setStringPermissions(userService.getPermissionValueListByUserId(userId));
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        String password = String.valueOf((char[]) token.getCredentials());
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new UnknownAccountException("帐号不存在！");
        }
        Security security = securityRepository.findOne(user.getId());
        if (!security.getPassword().equals(password)) {
            throw new IncorrectCredentialsException("密码不正确！");
        }
        return new SimpleAuthenticationInfo(user.getId(), security.getPassword(), getName());
    }
}
