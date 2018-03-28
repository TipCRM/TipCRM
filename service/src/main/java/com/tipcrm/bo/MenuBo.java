package com.tipcrm.bo;
import java.util.ArrayList;
import java.util.List;

public class MenuBo {

    private Integer id;
    private String name;
    private String title;
    private String content;
    private String icon;
    private String url;
    private List<MenuBo> children = new ArrayList<MenuBo>();

    public MenuBo(Integer id, String name, String title, String content, String icon, String url, List<MenuBo> children) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.content = content;
        this.icon = icon;
        this.url = url;
        this.children = children;
    }

    public MenuBo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<MenuBo> getChildren() {
        return children;
    }

    public void setChildren(List<MenuBo> children) {
        this.children = children;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
