package com.tipcrm.service;
import java.util.Set;

public interface RoleService {

    Set<String> getRoleListByUserId(Integer userId);
}
