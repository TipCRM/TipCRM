package com.tipcrm.bo;

import java.math.BigDecimal;

public class SaveLevelBo {

    private Integer id;

    private String name;

    private BigDecimal defaultPayment;

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

    public BigDecimal getDefaultPayment() {
        return defaultPayment;
    }

    public void setDefaultPayment(BigDecimal defaultPayment) {
        this.defaultPayment = defaultPayment;
    }
}
