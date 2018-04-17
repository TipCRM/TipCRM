package com.tipcrm.web.config;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileConfig {
    @Value("${uploadfile.base-dir}")
    private String baseUrl;

    private Logger logger = LoggerFactory.getLogger(FileConfig.class);

    @PostConstruct
    public void init() {
        logger.info("Read base url from application config =======> " + baseUrl);
        com.tipcrm.constant.FileConfig.baseUrl = baseUrl;
    }
}
