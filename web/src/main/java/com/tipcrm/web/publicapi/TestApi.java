package com.tipcrm.web.publicapi;

import com.tipcrm.service.WebContext;
import com.tipcrm.web.util.JsonEntity;
import com.tipcrm.web.util.ResponseHelper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/public/api/")
public class TestApi {

    private Logger logger = LoggerFactory.getLogger(TestApi.class);

    @Autowired
    private WebContext webContext;

    // @RequiresPermissions(value = "user:view")
    @RequestMapping(value = "currentUserName", method = RequestMethod.GET)
    public JsonEntity<String> getUser() {
        logger.debug(webContext.getCurrentUserId() + ":" + webContext.getCurrentUserName());
        return ResponseHelper.createInstance(webContext.getCurrentUserName());
    }
}
