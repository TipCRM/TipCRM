package com.tipcrm.service;

import java.util.List;

import com.tipcrm.bo.CreateUserBo;
import com.tipcrm.bo.DismissBo;
import com.tipcrm.bo.LoginBo;
import com.tipcrm.bo.QueryRequestBo;
import com.tipcrm.bo.QueryResultBo;
import com.tipcrm.bo.UpdateUserBo;
import com.tipcrm.bo.UserBasicBo;
import com.tipcrm.bo.UserBo;
import com.tipcrm.bo.UserDepartmentAssignBo;
import com.tipcrm.bo.UserExtBo;
import com.tipcrm.bo.UserLevelAssignBo;
import com.tipcrm.dao.entity.User;

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

    void userDepartmentAssign(UserDepartmentAssignBo assignBo);

    void userLevelAssign(UserLevelAssignBo assignBo);

    void dismiss(DismissBo dismissBo);
}
