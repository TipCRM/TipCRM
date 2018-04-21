package com.tipcrm.service;

import com.tipcrm.bo.MenuBo;
import com.tipcrm.dao.entity.Menu;

import java.util.List;

public interface MenuService {

    List<MenuBo> getAllMenus();

    List<MenuBo> findMenuByUserId(Integer userId);

    List<Menu> flatMenu(List<Menu> all, List<Menu> menus);

    void deactiveMenu(List<Integer> ids);
}
