package com.tipcrm.web.publicapi;
import java.util.List;

import com.tipcrm.bo.PermissionGroupBo;
import com.tipcrm.service.PermissionService;
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
@RequestMapping(value = "/public/api")
@Api
@RequiresAuthentication
public class PermissionApi {

    @Autowired
    private WebContext webContext;

    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "permission/me", method = RequestMethod.GET)
    public JsonEntity<List<PermissionGroupBo>> myPermissions() {
        Integer userId = webContext.getCurrentUserId();
        return ResponseHelper.createInstance(permissionService.getPermissionsByUserId(userId));
    }
}
