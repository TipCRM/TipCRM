package com.tipcrm.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "customer_approval")
public class CustomerApproval extends BaseCreateEntity {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "opt_type_id")
    private ListBox optType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_type_id")
    private ListBox reviewType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private ListBox status;

    @Column(name = "address")
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_user_id")
    private User followUser;

    @ManyToOne
    @JoinColumn(name = "follow_department_id")
    private Department followDepartment;

    @Column(name = "note")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_status_id")
    private ListBox reviewStatus;

    @Column(name = "final_approval_time")
    private Date finalApprovalTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ListBox getOptType() {
        return optType;
    }

    public void setOptType(ListBox optType) {
        this.optType = optType;
    }

    public ListBox getReviewType() {
        return reviewType;
    }

    public void setReviewType(ListBox reviewType) {
        this.reviewType = reviewType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ListBox getStatus() {
        return status;
    }

    public void setStatus(ListBox status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getFollowUser() {
        return followUser;
    }

    public void setFollowUser(User followUser) {
        this.followUser = followUser;
    }

    public Department getFollowDepartment() {
        return followDepartment;
    }

    public void setFollowDepartment(Department followDepartment) {
        this.followDepartment = followDepartment;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ListBox getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(ListBox reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Date getFinalApprovalTime() {
        return finalApprovalTime == null ? null : (Date) finalApprovalTime.clone();
    }

    public void setFinalApprovalTime(Date finalApprovalTime) {
        this.finalApprovalTime = finalApprovalTime == null ? null : (Date) finalApprovalTime.clone();
    }
}
