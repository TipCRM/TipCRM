package com.tipcrm.bo;

import java.util.Date;

public class DepartmentBo {
    private Integer id;

    private String name;

    private Integer parentId;

    private String parentName;

    private Integer managerId;

    private String managerName;

    private String entryUser;

    private Date entryTime;

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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getEntryUser() {
        return entryUser;
    }

    public void setEntryUser(String entryUser) {
        this.entryUser = entryUser;
    }

    public Date getEntryTime() {
        return entryTime == null ? null : (Date) entryTime.clone();
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime == null ? null : (Date) entryTime.clone();
    }
}
