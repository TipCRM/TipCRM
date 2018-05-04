package com.tipcrm.bo;

import java.util.Date;

public class DepartmentBo {
    private Integer id;

    private String name;

    private Integer total;

    private UserBasicBo manager;

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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public UserBasicBo getManager() {
        return manager;
    }

    public void setManager(UserBasicBo manager) {
        this.manager = manager;
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
