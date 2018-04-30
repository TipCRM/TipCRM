package com.tipcrm.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseAllEntity extends BaseCreateAndUpdateEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "delete_id")
    private User deleteUser;

    @Column(name = "delete_time")
    private Date deleteTime;

    public User getDeleteUser() {
        return deleteUser;
    }

    public void setDeleteUser(User deleteUser) {
        this.deleteUser = deleteUser;
    }

    public Date getDeleteTime() {
        return this.deleteTime == null ? null : (Date) deleteTime.clone();
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime == null ? null : (Date) deleteTime.clone();
    }
}
