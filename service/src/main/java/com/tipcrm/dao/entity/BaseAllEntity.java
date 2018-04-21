package com.tipcrm.dao.entity;

import javax.persistence.*;
import java.util.Date;

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
