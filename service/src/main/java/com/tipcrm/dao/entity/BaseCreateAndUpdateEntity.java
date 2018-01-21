package com.tipcrm.dao.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseCreateAndUpdateEntity extends BaseCreateEntity{
    @Column(name = "update_id")
    private Long updateId;

    @Column(name = "update_time")
    private Date updateTime;

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
