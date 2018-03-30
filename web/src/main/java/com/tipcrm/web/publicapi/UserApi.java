package com.tipcrm.web.publicapi;

import com.tipcrm.bo.UserBo;
import com.tipcrm.service.UserService;
import com.tipcrm.service.WebContext;
import com.tipcrm.web.util.JsonEntity;
import com.tipcrm.web.util.ResponseHelper;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/public/api/")
@Api
public class UserApi {

    @Autowired
    private WebContext webContext;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "user/me", method = RequestMethod.GET)
    @RequiresAuthentication
    public JsonEntity<UserBo> getUser() {
        Integer userId = webContext.getCurrentUserId();
        return ResponseHelper.createInstance(userService.getUserByUserId(userId));
    }

}
