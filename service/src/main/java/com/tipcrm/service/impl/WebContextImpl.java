package com.tipcrm.service.impl;
import com.tipcrm.dao.entity.User;
import com.tipcrm.dao.repository.UserRepository;
import com.tipcrm.service.WebContext;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebContextImpl implements WebContext{
    @Autowired
    private UserRepository userRepository;

    @Override
    public Long getCurrentUserId() {
        return (Long) SecurityUtils.getSubject().getPrincipal();
    }

    @Override
    public String getCurrentUserName() {
        User user = userRepository.findOne(getCurrentUserId());
        return user.getUserName();
    }
}
