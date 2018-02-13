package com.tipcrm.service;
import java.util.Set;

public interface PermissionService {

    Set<String> getPermissionValueListByUserId(Integer userId);
}
