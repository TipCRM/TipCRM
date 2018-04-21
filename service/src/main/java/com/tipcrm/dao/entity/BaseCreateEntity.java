package com.tipcrm.dao.entity;

import javax.persistence.*;
import java.util.Date;

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
