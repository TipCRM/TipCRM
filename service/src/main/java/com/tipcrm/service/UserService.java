package com.tipcrm.service;
import java.util.List;
import java.util.Set;

import com.tipcrm.bo.LoginBo;
import com.tipcrm.bo.RegistBo;
import com.tipcrm.bo.UserBo;
import com.tipcrm.constant.Constants;
import com.tipcrm.dao.entity.User;
import org.springframework.cache.annotation.Cacheable;

public interface UserService {


    User save(User user);

    User findOne(Integer userId);

    User findByEmailOrPhoneNo(String key);

    String regist(RegistBo registBo) throws Exception;

    void login(LoginBo loginBo) throws Exception;

    UserBo getUserByUserId(Integer userId) throws Exception;
}
