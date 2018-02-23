package com.tipcrm.service.impl;
import com.tipcrm.dao.entity.User;
import com.tipcrm.dao.repository.UserRepository;
import com.tipcrm.service.WebContext;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WebContextImpl implements WebContext {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Integer getCurrentUserId() {
        return (Integer) SecurityUtils.getSubject().getPrincipal();
    }

    @Override
    public User getCurrentUser() {
        Integer userId = getCurrentUserId();
        if (userId == null) {
            return null;
        }
        User user = userRepository.findOne(userId);
        return user;
    }
}
