package com.tipcrm.dao.entity;
import java.util.Date;

import javax.persistence.Column;

public class BaseCreateEntity {

    @Column(name = "entry_id")
    private Long entryId;

    @Column(name = "entry_time")
    private Date entryTime;

    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }
}
