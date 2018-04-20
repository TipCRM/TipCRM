package com.tipcrm.web.publicapi;

import java.util.List;

import com.tipcrm.bo.QueryRequestBo;
import com.tipcrm.bo.QueryResultBo;
import com.tipcrm.bo.UserBasicBo;
import com.tipcrm.bo.UserBo;
import com.tipcrm.bo.UserExtBo;
import com.tipcrm.constant.Constants;
import com.tipcrm.service.UserService;
import com.tipcrm.service.WebContext;
import com.tipcrm.web.util.JsonEntity;
import com.tipcrm.web.util.ResponseHelper;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/public/api/")
@RequiresAuthentication
@Api
public class UserApi {

    @Autowired
    private WebContext webContext;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "user/me", method = RequestMethod.GET)
    public JsonEntity<UserExtBo> getMe() {
        Integer userId = webContext.getCurrentUserId();
        return ResponseHelper.createInstance(userService.getUserByUserId(userId));
    }

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public JsonEntity<List<UserBasicBo>> queryUserByName(@RequestParam("userName") String userName, @RequestParam("includeDismiss") Boolean includeDismiss) {
        return ResponseHelper.createInstance(userService.findByName(userName, includeDismiss));
    }

    @RequestMapping(value = "user/query", method = RequestMethod.POST)
    public JsonEntity<QueryResultBo<UserBo>> queryUser(@RequestBody QueryRequestBo queryRequestBo) {
        return ResponseHelper.createInstance(userService.queryUser(queryRequestBo));
    }

    @RequestMapping(value = "user/{userId}", method = RequestMethod.GET)
    @RequiresPermissions(value = {Constants.Permission.USER_DETAIL_VIEW})
    public JsonEntity<UserExtBo> getUser(@PathVariable(name = "userId") Integer userId) {
        return ResponseHelper.createInstance(userService.getUserByUserId(userId));
    }
}
