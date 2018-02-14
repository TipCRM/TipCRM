package com.tipcrm.web.publicapi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.common.collect.Lists;
import com.tipcrm.bo.UserBo;
import com.tipcrm.dao.entity.User;
import com.tipcrm.service.UserService;
import com.tipcrm.service.WebContext;
import com.tipcrm.web.util.JsonEntity;
import com.tipcrm.web.util.ResponseHelper;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/public/api/")
@Api
public class UserApi {

    private Logger logger = LoggerFactory.getLogger(UserApi.class);

    @Autowired
    private WebContext webContext;

    @Autowired
    private UserService userService;

    // @RequiresPermissions(value = "user:view")
    @RequestMapping(value = "currentUserInfo", method = RequestMethod.GET)
    @RequiresAuthentication
    public JsonEntity<UserBo> getUser() throws Exception {
        Integer userId = webContext.getCurrentUserId();
        return ResponseHelper.createInstance(userService.getUserByUserId(userId));

    }
}
