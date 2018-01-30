package com.tipcrm.service;
import java.util.List;
import java.util.Set;

public interface UserService {
    Set<String> getRoleListByUserId(Integer userId);

    Set<String> getPermissionValueListByUserId(Integer userId);
}
