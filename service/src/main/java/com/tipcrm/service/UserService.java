package com.tipcrm.service;
import java.util.List;
import java.util.Set;

import com.tipcrm.bo.LoginBo;
import com.tipcrm.bo.RegistBo;

public interface UserService {
    Set<String> getRoleListByUserId(Integer userId);

    Set<String> getPermissionValueListByUserId(Integer userId);

    String regist(RegistBo registBo) throws Exception;

    void login(LoginBo loginBo) throws Exception;
}
