package com.tipcrm.web.publicapi;

import com.tipcrm.service.MailService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api
@RequestMapping(value = "/public/api")
public class MailApi {

    @Autowired
    private MailService mailService;

    @RequestMapping(value = "mail", method = RequestMethod.GET)
    public String sendMail(@RequestParam("to") String to, @RequestParam("subject") String subject, @RequestParam("content") String content) {
        mailService.sendSimpleEmail(to, subject, content);
        return "success";
    }
}
