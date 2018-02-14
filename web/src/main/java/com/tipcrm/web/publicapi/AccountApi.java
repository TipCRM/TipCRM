package com.tipcrm.web.publicapi;

import com.tipcrm.bo.LoginBo;
import com.tipcrm.bo.RegistBo;
import com.tipcrm.exception.AccountException;
import com.tipcrm.service.UserService;
import com.tipcrm.web.util.JsonEntity;
import com.tipcrm.web.util.ResponseHelper;
import io.swagger.annotations.Api;
import org.apache.shiro.authc.IncorrectCredentialsException;
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
public class AccountApi {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(AccountApi.class);

    @RequestMapping(value = "login", method = {RequestMethod.POST})
    public JsonEntity<String> login(@RequestBody LoginBo loginBo) throws Exception {
        userService.login(loginBo);
        return ResponseHelper.createInstance("success");
    }

    @RequestMapping(value = "regist", method = {RequestMethod.POST})
    public JsonEntity<String> regist(@RequestBody RegistBo registBo) throws Exception {
        String name = userService.regist(registBo);
        return ResponseHelper.createInstance(name);
    }

}
