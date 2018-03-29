package com.tipcrm.web.publicapi;
import java.util.List;

import com.tipcrm.bo.RoleBo;
import com.tipcrm.service.RoleService;
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
public class RoleApi {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "roles", method = RequestMethod.GET)
    public JsonEntity<List<RoleBo>> getRoles() {
        return ResponseHelper.createInstance(roleService.getAllRoles());
    }

}
