package com.tipcrm.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.tipcrm"})
@EntityScan(basePackages = "com.tipcrm.dao.entity")
@EnableJpaRepositories(basePackages = "com.tipcrm.dao.repository")
@EnableCaching
public class TipCRMApplication {

    public static void main(String[] args) {
        SpringApplication.run(TipCRMApplication.class, args);
    }
}
