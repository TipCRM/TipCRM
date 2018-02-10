package com.tipcrm.web.publicapi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.common.collect.Lists;
import com.tipcrm.bo.UserBo;
import com.tipcrm.service.WebContext;
import com.tipcrm.web.util.JsonEntity;
import com.tipcrm.web.util.ResponseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/public/api/")
public class UserApi {

    private Logger logger = LoggerFactory.getLogger(UserApi.class);

    @Autowired
    private WebContext webContext;

    // @RequiresPermissions(value = "user:view")
    @RequestMapping(value = "currentUserInfo", method = RequestMethod.GET)
    public JsonEntity<UserBo> getUser() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.debug(webContext.getCurrentUserId() + ":" + webContext.getCurrentUserName());
        UserBo userBo = new UserBo();
        userBo.setUserName("小窍门");
        userBo.setAvatar("https://cdnq.duitang.com/uploads/item/201506/26/20150626174017_LtvMR.thumb.224_0.jpeg");
        userBo.setBirthday(simpleDateFormat.parse("1994-09-08 00:00:00"));
        userBo.setDepartment("销售一部");
        userBo.setEmail("1051750377@qq.com");
        userBo.setHirer("高力");
        userBo.setHireTime(simpleDateFormat.parse("2017-11-03 00:00:00"));
        userBo.setIdCard("51118119940908****");
        userBo.setLevel("新员工");
        userBo.setManager("高力");
        userBo.setPhoneNo("15881193175");
        userBo.setStatus("在职");
        userBo.setRoles(Lists.newArrayList("总经理", "部门经理"));
        return ResponseHelper.createInstance(userBo);
    }
}
