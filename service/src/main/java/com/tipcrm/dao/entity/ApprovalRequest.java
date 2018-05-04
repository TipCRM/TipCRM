package com.tipcrm.dao.entity;

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
@Table(name = "approval_request")
public class ApprovalRequest {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_type")
    private ListBox approvalType;

    @Column(name = "approval_id")
    private Integer approvalId;

    @Column(name = "sequence")
    private Integer sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private User reviewer;

    @Column(name = "review_time")
    private Date reviewTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_status_id")
    private ListBox reviewStatus;

    @Column(name = "review_note")
    private String reviewNote;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ListBox getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(ListBox approvalType) {
        this.approvalType = approvalType;
    }

    public Integer getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(Integer approvalId) {
        this.approvalId = approvalId;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
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
