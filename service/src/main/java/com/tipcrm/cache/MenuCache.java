package com.tipcrm.cache;
import java.util.ArrayList;
import java.util.List;

import com.tipcrm.dao.entity.Menu;

public class MenuCache {
    private static List<Menu> menus = new ArrayList<>();
    private static List<Menu> deactiveMenus = new ArrayList<>();

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
        MenuCache.deactiveMenus = deactiveMenus;
    }
}
