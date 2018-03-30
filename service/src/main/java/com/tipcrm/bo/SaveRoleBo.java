package com.tipcrm.bo;
import java.util.ArrayList;
import java.util.List;

public class SaveRoleBo {
    private Integer id;
    private String name;
    private List<Integer> permissions = new ArrayList<>();

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

    public List<Integer> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Integer> permissions) {
        this.permissions = permissions;
    }
}
