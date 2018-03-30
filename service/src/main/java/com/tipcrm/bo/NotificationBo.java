package com.tipcrm.bo;

import java.util.Date;

public class NotificationBo {
    private Integer id;
    private String subject;
    private String content;
    private String sender;
    private Date time;
    private String status;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time == null ? null : (Date) time.clone();
    }

    public void setTime(Date time) {
        this.time = time == null ? null : (Date) time.clone();
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
