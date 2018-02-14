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
@Table(name = "communication")
public class Communication extends BaseCreateAndUpdateEntity{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_contact_id")
    private CustomerContact customerContact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follow_user_id")
    private User follower;

    @Column(name = "communicate_time")
    private Date communicateTime;

    @Column(name = "note")
    private String note;

    @Column(name = "next_communicate_time")
    private Date nextCommunicateTime;

    @Column(name = "on_side")
    private Boolean onSide;

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

    public CustomerContact getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(CustomerContact customerContact) {
        this.customerContact = customerContact;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public Date getCommunicateTime() {
        return communicateTime;
    }

    public void setCommunicateTime(Date communicateTime) {
        this.communicateTime = communicateTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getNextCommunicateTime() {
        return nextCommunicateTime;
    }

    public void setNextCommunicateTime(Date nextCommunicateTime) {
        this.nextCommunicateTime = nextCommunicateTime;
    }

    public Boolean getOnSide() {
        return onSide;
    }

    public void setOnSide(Boolean onSide) {
        this.onSide = onSide;
    }
}
