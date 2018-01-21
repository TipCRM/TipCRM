package com.tipcrm.service;
import java.util.List;
import java.util.Set;

public interface UserService {
    Set<String> getRoleListByUserId(Long userId);

    Set<String> getPermissionValueListByUserId(Long userId);
}
