package com.tipcrm.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.tipcrm.dao.entity.MenuPermission;
import org.springframework.util.CollectionUtils;

public class MenuPermissionBo implements Serializable {

    private Integer menuId;
    private String menuName;
    private List<PermissionBo> permissions = new ArrayList<>();

    public static List<MenuPermissionBo> toMenuPermissionBos(List<MenuPermission> menuPermissions) {
        List<MenuPermissionBo> groupBos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(menuPermissions)) {
            for (MenuPermission group : menuPermissions) {
                MenuPermissionBo groupBo = toMenuPermissionBo(group);
                if (groupBo != null) {
                    groupBos.add(groupBo);
                }
            }
        }
        return groupBos;
    }

    public static MenuPermissionBo toMenuPermissionBo(MenuPermission menuPermission) {
        if (menuPermission == null) {
            return null;
        }
        MenuPermissionBo groupBo = new MenuPermissionBo();
        groupBo.setMenuId(menuPermission.getMenu().getId());
        groupBo.setMenuName(menuPermission.getMenu().getDisplayName());
        groupBo.getPermissions().add(PermissionBo.toPermissionBo(menuPermission.getPermission()));
        return groupBo;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public List<PermissionBo> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionBo> permissions) {
        this.permissions = permissions;
    }
}
