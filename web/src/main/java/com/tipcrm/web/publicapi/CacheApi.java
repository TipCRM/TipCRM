package com.tipcrm.web.publicapi;

import com.tipcrm.constant.Constants;
import com.tipcrm.web.initializer.CacheInitializer;
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
public class CacheApi {
    @Autowired
    private CacheInitializer cacheInitializer;

    @RequestMapping(value = "cache/refresh/all", method = RequestMethod.POST)
    public JsonEntity<String> refreshAllCache() {
        cacheInitializer.run();
        return ResponseHelper.createInstance(Constants.RequestResult.SUCCESS);
    }

    @RequestMapping(value = "cache/refresh/configuration", method = RequestMethod.POST)
    public JsonEntity<String> refreshConfigurationCache() {
        cacheInitializer.initConfigurationCache();
        return ResponseHelper.createInstance(Constants.RequestResult.SUCCESS);
    }

    @RequestMapping(value = "cache/refresh/listBox", method = RequestMethod.POST)
    public JsonEntity<String> refreshListBoxCache() {
        cacheInitializer.initListBoxCache();
        return ResponseHelper.createInstance(Constants.RequestResult.SUCCESS);
    }

    @RequestMapping(value = "cache/refresh/roleAndPermission", method = RequestMethod.POST)
    public JsonEntity<String> refreshRoleAndPermissionCache() {
        cacheInitializer.initRoleAndPermissionCache();
        return ResponseHelper.createInstance(Constants.RequestResult.SUCCESS);
    }
}
