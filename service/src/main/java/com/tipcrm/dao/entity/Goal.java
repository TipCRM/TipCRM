package com.tipcrm.dao.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "goal")
public class Goal extends BaseCreateAndUpdateEntity{
    @Id
    @GeneratedValue
    @Column(name = "oid")
    private Integer id;

    @Column(name = "goal")
    private BigDecimal goal;

    @Column(name = "goal_time")
    private Date goalTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_type")
    private ListBox entityType;

    @Column(name = "entity_id")
    private Integer entityId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getGoal() {
        return goal;
    }

    public void setGoal(BigDecimal goal) {
        this.goal = goal;
    }

    public Date getGoalTime() {
        return goalTime;
    }

    public void setGoalTime(Date goalTime) {
        this.goalTime = goalTime;
    }

    public ListBox getEntityType() {
        return entityType;
    }

    public void setEntityType(ListBox entityType) {
        this.entityType = entityType;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }
}
