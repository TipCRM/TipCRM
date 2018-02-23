package com.tipcrm.service;
import com.tipcrm.exception.BizException;

public interface MailService {
    void sendSimpleEmail(String to, String subject, String content) throws BizException;
}
