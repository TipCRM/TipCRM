package com.tipcrm.dao.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "contract")
public class Contract {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_user_id")
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_department_id")
    private Department department;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "note")
    private String note;

    @Column(name = "sign_time")
    private Date signTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private User reviewer;

    @Column(name = "review_time")
    private Date reviewTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_status_id")
    private ListBox reviewStatus;

    @Column(name = "review_note")
    private String reviewNote;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getSignTime() {
        return signTime == null ? null : (Date) signTime.clone();
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime == null ? null : (Date) signTime.clone();
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public Date getReviewTime() {
        return reviewTime == null ? null : (Date) reviewTime.clone();
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime == null ? null : (Date) reviewTime.clone();
    }

    public ListBox getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(ListBox reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getReviewNote() {
        return reviewNote;
    }

    public void setReviewNote(String reviewNote) {
        this.reviewNote = reviewNote;
    }
}
