package com.tipcrm.bo;

import java.math.BigDecimal;

public class UserLevelAssignBo {
    private Integer userId;

    private Integer levelId;

    private BigDecimal paymentPercentage;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public BigDecimal getPaymentPercentage() {
        return paymentPercentage;
    }

    public void setPaymentPercentage(BigDecimal paymentPercentage) {
        this.paymentPercentage = paymentPercentage;
    }
}
