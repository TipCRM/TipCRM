package com.tipcrm.service;

import java.util.List;

import com.tipcrm.bo.MenuBo;
import com.tipcrm.dao.entity.Menu;

public interface MenuService {

    List<MenuBo> getAllMenus();

    List<MenuBo> findMenuByUserId(Integer userId);

    List<Menu> flatMenu(List<Menu> all, List<Menu> menus);

    void deactiveMenu(List<Integer> ids);
}
