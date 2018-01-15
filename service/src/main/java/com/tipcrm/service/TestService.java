package com.tipcrm.service;

import com.tipcrm.dao.entity.TestEntity;

public interface TestService {
    TestEntity findById(Long id);
}
