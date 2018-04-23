package com.tipcrm.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserExtBo extends UserBo {

    private String idCard;

    private Date birthday;

    private String avatar;

    private BigDecimal paymentPercentage;

    private String hirer;

    private Date hireTime;

    private String dismissUser;

    private Date dismissDate;

    private String dismissReason;

    private List<String> roles = new ArrayList<String>();

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Date getBirthday() {
        return birthday == null ? null : (Date) birthday.clone();
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday == null ? null : (Date) birthday.clone();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getHirer() {
        return hirer;
    }

    public void setHirer(String hirer) {
        this.hirer = hirer;
    }

    public Date getHireTime() {
        return hireTime == null ? null : (Date) hireTime.clone();
    }

    public void setHireTime(Date hireTime) {
        this.hireTime = hireTime == null ? null : (Date) hireTime.clone();
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getDismissUser() {
        return dismissUser;
    }

    public void setDismissUser(String dismissUser) {
        this.dismissUser = dismissUser;
    }

    public Date getDismissDate() {
        return dismissDate == null ? null : (Date) dismissDate.clone();
    }

    public void setDismissDate(Date dismissDate) {
        this.dismissDate = dismissDate == null ? null : (Date) dismissDate.clone();
    }

    public String getDismissReason() {
        return dismissReason;
    }

    public void setDismissReason(String dismissReason) {
        this.dismissReason = dismissReason;
    }

    public BigDecimal getPaymentPercentage() {
        return paymentPercentage;
    }

    public void setPaymentPercentage(BigDecimal paymentPercentage) {
        this.paymentPercentage = paymentPercentage;
    }
}
