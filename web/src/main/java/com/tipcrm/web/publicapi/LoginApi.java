package com.tipcrm.web.publicapi;

import com.tipcrm.bo.LoginBo;
import com.tipcrm.bo.RegistBo;
import com.tipcrm.service.UserService;
import com.tipcrm.web.util.JsonEntity;
import com.tipcrm.web.util.ResponseHelper;
import io.swagger.annotations.Api;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/public/api")
@Api
public class LoginApi {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(LoginApi.class);

    @RequestMapping(value = "login", method = {RequestMethod.POST})
    public JsonEntity<String> login(@RequestBody LoginBo loginBo) throws Exception {
        try {
            userService.login(loginBo);
            return ResponseHelper.createInstance("success");
        } catch (UnknownAccountException | IncorrectCredentialsException e) {
            throw new IncorrectCredentialsException("账号或密码不正确");
        } catch (Exception e) {
            throw new Exception("登陆失败，请联系管理员");
        }
    }

    @RequestMapping(value = "regist", method = {RequestMethod.POST})
    public JsonEntity<String> regist(@RequestBody RegistBo registBo) throws Exception {
        String name = userService.regist(registBo);
        return ResponseHelper.createInstance(name);
    }

}
