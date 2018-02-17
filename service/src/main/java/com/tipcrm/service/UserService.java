package com.tipcrm.service;
import com.tipcrm.bo.CreateUserBo;
import com.tipcrm.bo.LoginBo;
import com.tipcrm.bo.RegistUserBo;
import com.tipcrm.bo.UserBo;
import com.tipcrm.exception.BizException;

public interface UserService {

    String regist(RegistUserBo registUserBo) throws Exception;

    String saveUser(CreateUserBo createUserBo) throws Exception;

    void login(LoginBo loginBo) throws Exception;

    UserBo getUserByUserId(Integer userId) throws Exception;

    Boolean isUserExist(Integer userId) throws BizException;
}
