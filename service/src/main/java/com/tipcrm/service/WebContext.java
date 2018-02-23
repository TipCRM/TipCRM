package com.tipcrm.service;
import com.tipcrm.dao.entity.User;

public interface WebContext {

    Integer getCurrentUserId();

    User getCurrentUser();
}
