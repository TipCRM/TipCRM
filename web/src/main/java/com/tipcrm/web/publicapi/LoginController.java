package com.tipcrm.web.publicapi;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tipcrm.web.util.JsonEntity;
import com.tipcrm.web.util.ResponseHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/public/api")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "login", method = {RequestMethod.POST})
    public JsonEntity<String> login(String username, String password) {
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            SecurityUtils.getSubject().login(token);
            return ResponseHelper.createInstance("登陆成功");
        } catch (UnknownAccountException e) {
            logger.info("账号不存在");
            return new JsonEntity<>(500, "账号不存在");
        } catch (IncorrectCredentialsException e) {
            logger.info("密码不正确");
            return new JsonEntity<>(500, "密码不正确");
        } catch (Exception e) {
            return new JsonEntity<>(500, "登陆失败");
        }
    }
}
