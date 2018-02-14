package com.tipcrm.service;
import java.util.List;
import java.util.Set;

import com.tipcrm.bo.LoginBo;
import com.tipcrm.bo.RegistBo;
import com.tipcrm.bo.UserBo;

public interface UserService {


    String regist(RegistBo registBo) throws Exception;

    void login(LoginBo loginBo) throws Exception;

    UserBo getUserByUserId(Integer userId) throws Exception;
}
