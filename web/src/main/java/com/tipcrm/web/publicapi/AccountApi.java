package com.tipcrm.web.publicapi;

import com.tipcrm.bo.CreateUserBo;
import com.tipcrm.bo.LoginBo;
import com.tipcrm.constant.Constants;
import com.tipcrm.service.UserService;
import com.tipcrm.web.util.JsonEntity;
import com.tipcrm.web.util.ResponseHelper;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public JsonEntity<String> login(@RequestBody LoginBo loginBo) {
        userService.login(loginBo);
        return ResponseHelper.createInstance("success");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @RequiresAuthentication
    public JsonEntity<String> logout() {
        userService.logout();
        return ResponseHelper.createInstance("success");
    }

    @RequestMapping(value = "/user", method = {RequestMethod.POST})
    @RequiresAuthentication
    @RequiresPermissions(value = Constants.Permission.USER_ADD)
    public JsonEntity<Integer> addUser(@RequestBody CreateUserBo saveUserBo) {
        Integer workNo = userService.saveUser(saveUserBo);
        return ResponseHelper.createInstance(workNo);
    }

}
