package com.tipcrm.web.publicapi;
import java.util.ArrayList;
import java.util.List;

import com.tipcrm.bo.MenuBo;
import com.tipcrm.web.util.JsonEntity;
import com.tipcrm.web.util.ResponseHelper;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/public/api/")
@Api
public class MenuApi {

    @RequestMapping(value = "menu", method = RequestMethod.GET)
    @RequiresAuthentication
    public JsonEntity<List<MenuBo>> getMenu(){
        List<MenuBo> menuBos = new ArrayList<>();
        MenuBo menuBo = new MenuBo();
        menuBo.setName("主页");
        menuBo.setIcon("home");
        menuBo.setUrl("index");
        menuBos.add(menuBo);
        menuBo = new MenuBo();
        menuBo.setName("我的客户");
        menuBo.setIcon("team");
        menuBo.setUrl("my/customer");
        menuBos.add(menuBo);
        menuBo = new MenuBo();
        menuBo.setName("销售报表");
        menuBo.setIcon("pie-chart");
        menuBo.setUrl("my/summary");
        menuBos.add(menuBo);
        menuBo = new MenuBo();
        menuBo.setName("财务管理");
        menuBo.setIcon("pay-circle-o");
        menuBo.setUrl("finance");
        menuBos.add(menuBo);
        menuBo = new MenuBo();
        menuBo.setName("资料库");
        menuBo.setIcon("file-text");
        menuBo.setUrl("material");
        menuBos.add(menuBo);
        menuBo = new MenuBo();
        menuBo.setName("系统设置");
        menuBo.setIcon("setting");
        MenuBo subMenu = new MenuBo();
        subMenu.setName("客户管理");
        subMenu.setIcon("team");
        subMenu.setUrl("management/customer");
        menuBo.getMenuBos().add(subMenu);
        subMenu = new MenuBo();
        subMenu.setName("员工管理");
        subMenu.setIcon("usergroup-add");
        subMenu.setUrl("management/user");
        menuBo.getMenuBos().add(subMenu);
        subMenu = new MenuBo();
        subMenu.setName("审批管理");
        subMenu.setIcon("check-circle-o");
        subMenu.setUrl("management/approval");
        menuBo.getMenuBos().add(subMenu);
        subMenu = new MenuBo();
        subMenu.setName("权限管理");
        subMenu.setIcon("unlock");
        subMenu.setUrl("management/permission");
        menuBo.getMenuBos().add(subMenu);
        subMenu = new MenuBo();
        subMenu.setName("角色管理");
        subMenu.setIcon("solution");
        subMenu.setUrl("management/role");
        menuBo.getMenuBos().add(subMenu);
        subMenu = new MenuBo();
        subMenu.setName("资料管理");
        subMenu.setIcon("book");
        subMenu.setUrl("management/material");
        menuBo.getMenuBos().add(subMenu);
        menuBos.add(menuBo);
        return ResponseHelper.createInstance(menuBos);
    }
}
