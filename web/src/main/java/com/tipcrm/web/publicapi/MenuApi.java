package com.tipcrm.web.publicapi;

import com.tipcrm.bo.MenuBo;
import com.tipcrm.constant.Constants;
import com.tipcrm.service.MenuService;
import com.tipcrm.service.WebContext;
import com.tipcrm.web.util.JsonEntity;
import com.tipcrm.web.util.ResponseHelper;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/public/api/")
@Api
@RequiresAuthentication
public class MenuApi {

    @Autowired
    private WebContext webContext;

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "menu/me", method = RequestMethod.GET)
    public JsonEntity<List<MenuBo>> getMyMenu() {
        Integer userId = webContext.getCurrentUserId();
        List<MenuBo> menuBos = menuService.findMenuByUserId(userId);
        return ResponseHelper.createInstance(menuBos);
    }

    @RequestMapping(value = "menu/all", method = RequestMethod.GET)
    public JsonEntity<List<MenuBo>> getAllMenu() {
        List<MenuBo> menuBos = menuService.getAllMenus();
        return ResponseHelper.createInstance(menuBos);
    }

    @RequestMapping(value = "menu", method = RequestMethod.DELETE)
    @RequiresPermissions(Constants.Permission.SYSTEM_CONFIGURE)
    public JsonEntity<String> dactiveMenu(@RequestBody List<Integer> ids) {
        menuService.deactiveMenu(ids);
        return ResponseHelper.createInstance(Constants.RequestResult.SUCCESS);
    }
}
