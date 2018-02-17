package com.tipcrm.web.publicapi;
import java.util.List;

import com.tipcrm.bo.MenuBo;
import com.tipcrm.service.MenuService;
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
@RequestMapping(value = "/public/api/")
@Api
public class MenuApi {

    @Autowired
    private WebContext webContext;

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "menu", method = RequestMethod.GET)
    @RequiresAuthentication
    public JsonEntity<List<MenuBo>> getMenu() {
        Integer userId = webContext.getCurrentUserId();
        List<MenuBo> menuBos = menuService.findMenuByUserId(userId);
        return ResponseHelper.createInstance(menuBos);
    }
}
