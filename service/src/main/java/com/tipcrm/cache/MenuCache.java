package com.tipcrm.cache;

import com.tipcrm.dao.entity.Menu;
import com.tipcrm.dao.entity.MenuPermission;

import java.util.ArrayList;
import java.util.List;

public class MenuCache {
    /**
     * Flat menus
     */
    private static List<Menu> menus = new ArrayList<>();
    private static List<Menu> deactiveMenus = new ArrayList<>();
    private static List<MenuPermission> menuPermissions = new ArrayList<>();

    /**
     * Get Flat menus
     */
    public static List<Menu> getMenus() {
        return menus;
    }

    public static void setMenus(List<Menu> menus) {
        MenuCache.menus = menus;
    }

    public static List<Menu> getDeactiveMenus() {
        return deactiveMenus;
    }

    public static void setDeactiveMenus(List<Menu> deactiveMenus) {
        for (Menu menu : deactiveMenus) {
            menu.setActive(false);
        }
        MenuCache.deactiveMenus = deactiveMenus;
    }

    public static List<MenuPermission> getMenuPermissions() {
        return menuPermissions;
    }

    public static void setMenuPermissions(List<MenuPermission> menuPermissions) {
        MenuCache.menuPermissions = menuPermissions;
    }

    public static List<MenuPermission> getMenuPermissions(Integer menuId) {
        List<MenuPermission> res = new ArrayList<>();
        for (MenuPermission menuPermission : menuPermissions) {
            if (menuPermission.getMenu().getId().equals(menuId)) {
                res.add(menuPermission);
            }
        }
        return res;
    }
}
