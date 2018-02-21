package com.tipcrm.bo;
import java.math.BigDecimal;
import java.util.Date;

public class QueryCustomerBo {
    private Integer customerId;

    private String customerName;

    private String status;

    private Integer contactId;

    private String contactName;

    private String contactPhone;

    private Date lastCommunicationTime;

    private String lastCommunicationContent;

    private Date nextCommunicationTime;

    private BigDecimal intentionalAmount;

    private BigDecimal signAmount;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Date getLastCommunicationTime() {
        return lastCommunicationTime;
    }

    public void setLastCommunicationTime(Date lastCommunicationTime) {
        this.lastCommunicationTime = lastCommunicationTime;
    }

    public String getLastCommunicationContent() {
        return lastCommunicationContent;
    }

    public void setLastCommunicationContent(String lastCommunicationContent) {
        this.lastCommunicationContent = lastCommunicationContent;
    }

    public Date getNextCommunicationTime() {
        return nextCommunicationTime;
    }

    public void setNextCommunicationTime(Date nextCommunicationTime) {
        this.nextCommunicationTime = nextCommunicationTime;
    }

    public BigDecimal getIntentionalAmount() {
        return intentionalAmount;
    }

    public void setIntentionalAmount(BigDecimal intentionalAmount) {
        this.intentionalAmount = intentionalAmount;
    }

    public BigDecimal getSignAmount() {
        return signAmount;
    }

    public void setSignAmount(BigDecimal signAmount) {
        this.signAmount = signAmount;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }
}
