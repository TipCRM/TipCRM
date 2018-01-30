package com.tipcrm.dao.entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
    @GeneratedValue
    @Id
    @Column(name = "oid")
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

    @Column(name = "status")
    private Integer status;

    @Column(name = "hire_id")
    private Integer hireId;

    @Column(name = "hire_time")
    private Date hireTime;

    @Column(name = "manager_id")
    private Integer managerId;

    @Column(name = "department_id")
    private Integer departmentId;

    @Column(name = "level_id")
    private Integer levelId;

    @Column(name = "dismiss_id")
    private Integer dismissId;

    @Column(name = "dismiss_date")
    private Date dismissDate;

    @Column(name = "dismiss_reason")
    private String dismissReason;

    @Column(name = "update_id")
    private Integer updateId;

    @Column(name = "update_time")
    private Date updateTime;

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles = new ArrayList<Role>();

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
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getHireId() {
        return hireId;
    }

    public void setHireId(Integer hireId) {
        this.hireId = hireId;
    }

    public Date getHireTime() {
        return hireTime;
    }

    public void setHireTime(Date hireTime) {
        this.hireTime = hireTime;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public Integer getDismissId() {
        return dismissId;
    }

    public void setDismissId(Integer dismissId) {
        this.dismissId = dismissId;
    }

    public Date getDismissDate() {
        return dismissDate;
    }

    public void setDismissDate(Date dismissDate) {
        this.dismissDate = dismissDate;
    }

    public String getDismissReason() {
        return dismissReason;
    }

    public void setDismissReason(String dismissReason) {
        this.dismissReason = dismissReason;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
