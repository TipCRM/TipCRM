package com.tipcrm.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseCreateEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entry_id")
    private User entryUser;

    @Column(name = "entry_time")
    private Date entryTime;

    public User getEntryUser() {
        return entryUser;
    }

    public void setEntryUser(User entryUser) {
        this.entryUser = entryUser;
    }

    public Date getEntryTime() {
        return this.entryTime == null ? null : (Date) entryTime.clone();
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime == null ? null : (Date) entryTime.clone();
    }
}
