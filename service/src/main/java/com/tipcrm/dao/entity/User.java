package com.tipcrm.dao.entity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
    @GeneratedValue
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "id_card")
    private String idCard;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "motto")
    private String motto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status")
    private ListBox status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hire_id")
    private User hire;

    @Column(name = "hire_time")
    private Date hireTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id")
    private Level level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dismiss_id")
    private User dismissUser;

    @Column(name = "dismiss_date")
    private Date dismissTime;

    @Column(name = "dismiss_reason")
    private String dismissReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_id")
    private User updateUser;

    @Column(name = "update_time")
    private Date updateTime;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles = new ArrayList<Role>();

    @Column(name = "payment_percent")
    private BigDecimal paymentPercent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Date getBirthday() {
        return (Date) birthday.clone();
    }

    public void setBirthday(Date birthday) {
        this.birthday = (Date) birthday.clone();
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public ListBox getStatus() {
        return status;
    }

    public void setStatus(ListBox status) {
        this.status = status;
    }

    public User getHire() {
        return hire;
    }

    public void setHire(User hire) {
        this.hire = hire;
    }

    public Date getHireTime() {
        return (Date) hireTime.clone();
    }

    public void setHireTime(Date hireTime) {
        this.hireTime = (Date) hireTime.clone();
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public User getDismissUser() {
        return dismissUser;
    }

    public void setDismissUser(User dismissUser) {
        this.dismissUser = dismissUser;
    }

    public Date getDismissTime() {
        return (Date) dismissTime.clone();
    }

    public void setDismissTime(Date dismissTime) {
        this.dismissTime = (Date) dismissTime.clone();
    }

    public String getDismissReason() {
        return dismissReason;
    }

    public void setDismissReason(String dismissReason) {
        this.dismissReason = dismissReason;
    }

    public User getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(User updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return (Date) updateTime.clone();
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = (Date) updateTime.clone();
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public BigDecimal getPaymentPercent() {
        return paymentPercent;
    }

    public void setPaymentPercent(BigDecimal paymentPercent) {
        this.paymentPercent = paymentPercent;
    }
}
