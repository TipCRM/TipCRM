package com.tipcrm.service;

import com.tipcrm.dao.entity.Configuration;

public interface ConfigurationService {
    Configuration get(String key);

    void set(String key, String value);

    Integer generateNewWorkNo();
}
