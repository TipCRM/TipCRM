package com.tipcrm.web.config;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileConfig {
    @Value("${uploadfile.base-dir}")
    private String baseUrl;

    @PostConstruct
    public void init() {
        com.tipcrm.constant.FileConfig.baseUrl = baseUrl;
    }
}
