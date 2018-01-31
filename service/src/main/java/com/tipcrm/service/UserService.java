package com.tipcrm.service;
import java.util.List;
import java.util.Set;

public interface UserService {
    Set<String> getRoleListByUserId(Integer userId);

    Set<String> getPermissionValueListByUserId(Integer userId);

    String regist(String email, String password, String username, Boolean isManager) throws Exception;

    void login(String loginKey, String password) throws Exception;
}
