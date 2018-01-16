package com.tipcrm.web.publicapi;

import com.tipcrm.dao.entity.TestEntity;
import com.tipcrm.service.TestService;
import com.tipcrm.web.util.JsonEntity;
import com.tipcrm.web.util.ResponseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/public/api")
public class TestApi {

    private Logger logger = LoggerFactory.getLogger(TestApi.class);

    @Autowired
    private TestService testService;

    @RequestMapping(value = "query/{userId}", method = RequestMethod.GET)
    public JsonEntity<TestEntity> queryByUserId(@PathVariable("userId") Long id) {
        logger.info("id = " + id);
        return ResponseHelper.createInstance(testService.findById(id));
    }
}
