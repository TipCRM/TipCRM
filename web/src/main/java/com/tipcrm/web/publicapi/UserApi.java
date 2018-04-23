package com.tipcrm.web.publicapi;

import com.tipcrm.bo.ChangePasswordBo;
import com.tipcrm.bo.CreateUserBo;
import com.tipcrm.bo.DismissBo;
import com.tipcrm.bo.QueryRequestBo;
import com.tipcrm.bo.QueryResultBo;
import com.tipcrm.bo.UpdateUserBo;
import com.tipcrm.bo.UserBasicBo;
import com.tipcrm.bo.UserBo;
import com.tipcrm.bo.UserDepartmentAssignBo;
import com.tipcrm.bo.UserExtBo;
import com.tipcrm.bo.UserLevelAssignBo;
import com.tipcrm.constant.Constants;
import com.tipcrm.exception.BizException;
import com.tipcrm.service.UserService;
import com.tipcrm.service.WebContext;
import com.tipcrm.web.util.JsonEntity;
import com.tipcrm.web.util.ResponseHelper;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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

    @RequestMapping(value = "user/password", method = RequestMethod.PUT)
    public JsonEntity<String> changePassword(@RequestBody ChangePasswordBo changePasswordBo, HttpServletRequest request) {
        if (StringUtils.isBlank(changePasswordBo.getValidationCode())) {
            throw new BizException("验证码不能为空");
        }
        if (StringUtils.isBlank(changePasswordBo.getNewPassword())) {
            throw new BizException("新密码不能为空");
        }
        String correctValidationCode = (String) request.getSession().getAttribute(Constants.SessionAttribute.CHANG_PASSWORD_VALIDATION_CODE);
        if (StringUtils.isBlank(correctValidationCode)) {
            throw new BizException("请先获取验证码");
        }
        Date date = (Date) request.getSession().getAttribute(Constants.SessionAttribute.CHANG_PASSWORD_VALIDATION_CODE_TIME);
        Date now = new Date();
        int minutes = (int) ((now.getTime() - date.getTime()) / (1000 * 60));
        if (minutes > 30) {
            throw new BizException("验证码已过期，请重新获取");
        }
        Integer tryTimes = (Integer) request.getSession().getAttribute(Constants.SessionAttribute.CHANG_PASSWORD_VALIDATION_CODE_TRY_TIMES);
        if (tryTimes != null && tryTimes >= 3) {
            removeChangePasswordSessionAttribute(request);
            throw new BizException("尝试次数过多，请重新获取验证码");
        }
        if (!changePasswordBo.getValidationCode().equals(correctValidationCode)) {
            request.getSession().setAttribute(Constants.SessionAttribute.CHANG_PASSWORD_VALIDATION_CODE_TRY_TIMES, tryTimes + 1);
            throw new BizException("验证码不正确");
        }
        userService.changePassword(changePasswordBo.getNewPassword());
        removeChangePasswordSessionAttribute(request);
        return ResponseHelper.createInstance(Constants.RequestResult.SUCCESS);
    }

    private void removeChangePasswordSessionAttribute(HttpServletRequest request) {
        request.getSession().removeAttribute(Constants.SessionAttribute.CHANG_PASSWORD_VALIDATION_CODE);
        request.getSession().removeAttribute(Constants.SessionAttribute.CHANG_PASSWORD_VALIDATION_CODE_TIME);
        request.getSession().removeAttribute(Constants.SessionAttribute.CHANG_PASSWORD_VALIDATION_CODE_TRY_TIMES);
    }

    @RequestMapping(value = "user/department", method = RequestMethod.PUT)
    @RequiresPermissions(Constants.Permission.USER_UPDATE)
    public JsonEntity<String> assignDepartment(@RequestBody UserDepartmentAssignBo assignBo) {
        userService.userDepartmentAssign(assignBo);
        return ResponseHelper.createInstance(Constants.RequestResult.SUCCESS);
    }

    @RequestMapping(value = "user/level", method = RequestMethod.PUT)
    @RequiresPermissions(Constants.Permission.USER_UPDATE)
    public JsonEntity<String> assignLevel(@RequestBody UserLevelAssignBo assignBo) {
        userService.userLevelAssign(assignBo);
        return ResponseHelper.createInstance(Constants.RequestResult.SUCCESS);
    }

    @RequestMapping(value = "user", method = RequestMethod.DELETE)
    @RequiresPermissions(Constants.Permission.USER_DELETE)
    public JsonEntity<String> dismiss(@RequestBody DismissBo dismissBo) {
        userService.dismiss(dismissBo);
        return ResponseHelper.createInstance(Constants.RequestResult.SUCCESS);
    }
}
