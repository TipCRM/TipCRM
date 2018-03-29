package com.tipcrm.dao.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseCreateAndUpdateEntity extends BaseCreateEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "update_id")
    private User updateUser;

    @Column(name = "update_time")
    private Date updateTime;

    public User getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(User updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
