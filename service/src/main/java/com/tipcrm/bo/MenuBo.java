package com.tipcrm.bo;
import java.util.ArrayList;
import java.util.List;

public class MenuBo {

    private String name;
    private String icon;
    private String url;
    private List<MenuBo> menuBos = new ArrayList<MenuBo>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<MenuBo> getMenuBos() {
        return menuBos;
    }

    public void setMenuBos(List<MenuBo> menuBos) {
        this.menuBos = menuBos;
    }
}
