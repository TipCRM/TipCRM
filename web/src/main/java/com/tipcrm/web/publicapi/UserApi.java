package com.tipcrm.web.publicapi;

import com.tipcrm.bo.*;
import com.tipcrm.constant.Constants;
import com.tipcrm.service.UserService;
import com.tipcrm.service.WebContext;
import com.tipcrm.web.util.JsonEntity;
import com.tipcrm.web.util.ResponseHelper;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @RequestMapping(value = "/user", method = {RequestMethod.POST})
    @RequiresAuthentication
    @RequiresPermissions(value = Constants.Permission.USER_ADD)
    public JsonEntity<Integer> addUser(@RequestBody CreateUserBo saveUserBo) {
        Integer workNo = userService.saveUser(saveUserBo);
        return ResponseHelper.createInstance(workNo);
    }

    @RequestMapping(value = "user/me", method = RequestMethod.PUT)
    public JsonEntity<String> updateMe(@RequestBody UpdateUserBo updateUserBo) {
        userService.updateMe(updateUserBo);
        return ResponseHelper.createInstance(Constants.RequestResult.SUCCESS);
    }
}
