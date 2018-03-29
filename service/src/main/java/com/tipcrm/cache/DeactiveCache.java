package com.tipcrm.cache;
import java.util.ArrayList;
import java.util.List;

import com.tipcrm.bo.MenuCacheBo;
import com.tipcrm.bo.PermissionBo;
import org.springframework.util.CollectionUtils;

public class DeactiveCache {
    private static List<MenuCacheBo> deactiveMenus = new ArrayList<>();

    private static List<PermissionBo> deactivePermissions = new ArrayList<>();

    public static void updateMenus(List<MenuCacheBo> menuBos) {
        if (CollectionUtils.isEmpty(menuBos)) {
            return;
        }
        menuBos.forEach(menuBo -> deactiveMenus.add(menuBo));
    }

    public static List<MenuCacheBo> getDeactiveMenus() {
        return deactiveMenus;
    }

    public static void setDeactiveMenus(List<MenuCacheBo> deactiveMenu) {
        deactiveMenus = deactiveMenu;
    }

    public static List<PermissionBo> getDeactivePermissions() {
        return deactivePermissions;
    }

    public static void setDeactivePermissions(List<PermissionBo> deactivePermission) {
        deactivePermissions = deactivePermission;
    }
}
