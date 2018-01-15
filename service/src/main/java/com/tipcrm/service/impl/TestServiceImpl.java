package com.tipcrm.service.impl;
import com.tipcrm.dao.entity.TestEntity;
import com.tipcrm.dao.repository.TestRepository;
import com.tipcrm.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TestServiceImpl implements TestService{

    @Autowired
    private TestRepository testRepository;

    @Override
    public TestEntity findById(Long id) {
        return testRepository.findOne(id);
    }
}
