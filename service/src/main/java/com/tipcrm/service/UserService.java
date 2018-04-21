package com.tipcrm.service;

import com.tipcrm.bo.*;
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
}
