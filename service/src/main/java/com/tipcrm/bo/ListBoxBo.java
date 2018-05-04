package com.tipcrm.bo;

public class ListBoxBo {
    private Integer id;
    private String displayName;

    public ListBoxBo(Integer id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public ListBoxBo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
