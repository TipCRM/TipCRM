package com.tipcrm.service;

import com.tipcrm.bo.CreateUserBo;
import com.tipcrm.bo.LoginBo;
import com.tipcrm.bo.QueryRequestBo;
import com.tipcrm.bo.QueryResultBo;
import com.tipcrm.bo.UpdateUserBo;
import com.tipcrm.bo.UserBasicBo;
import com.tipcrm.bo.UserBo;
import com.tipcrm.bo.UserExtBo;
import com.tipcrm.dao.entity.User;

import java.util.List;

public interface UserService {

    Integer saveUser(CreateUserBo createUserBo);

    void login(LoginBo loginBo);

    UserExtBo getUserByUserId(Integer userId);

    Boolean isUserExist(Integer userId);

    Boolean isGeneralManager(Integer userId);

    List<User> findGeneralManager();

    User findSystemUser();

    void logout();

    List<UserBasicBo> findByName(String userName, Boolean includeDismiss);

    QueryResultBo<UserBo> queryUser(QueryRequestBo queryRequestBo);

    void updateMe(UpdateUserBo updateUserBo);

    String generateChangePasswordValidationCode(String email);

    void changePassword(String newPassword);
}
