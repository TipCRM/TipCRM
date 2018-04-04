package com.tipcrm.dao.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "level")
public class Level extends BaseAllEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "default_payment_percent")
    private BigDecimal defaultPaymentPercent;

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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public BigDecimal getDefaultPaymentPercent() {
        return defaultPaymentPercent;
    }

    public void setDefaultPaymentPercent(BigDecimal defaultPaymentPercent) {
        this.defaultPaymentPercent = defaultPaymentPercent;
    }

}