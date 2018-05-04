package com.tipcrm.web.publicapi;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.tipcrm.constant.Constants;
import com.tipcrm.constant.MailType;
import com.tipcrm.service.UserService;
import com.tipcrm.web.util.JsonEntity;
import com.tipcrm.web.util.ResponseHelper;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api
@RequestMapping(value = "/public/api")
@RequiresAuthentication
public class MailApi {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "mail", method = RequestMethod.GET)
    public JsonEntity<String> sendEmail(MailType type, String email, HttpServletRequest request) {
        if (type.equals(MailType.CHANGE_PASSWORD)) {
            String code = userService.generateChangePasswordValidationCode(email);
            request.getSession().setAttribute(Constants.SessionAttribute.CHANG_PASSWORD_VALIDATION_CODE, code);
            request.getSession().setAttribute(Constants.SessionAttribute.CHANG_PASSWORD_VALIDATION_CODE_TIME, new Date());
            request.getSession().setAttribute(Constants.SessionAttribute.CHANG_PASSWORD_VALIDATION_CODE_TRY_TIMES, 0);
        }
        return ResponseHelper.createInstance(Constants.RequestResult.SUCCESS);
    }
}
