package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableCaching
@ServletComponentScan
public class SpringBootVideoApplication {

    private final static Logger LOGGER = LoggerFactory.getLogger(SpringBootVideoApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(SpringBootVideoApplication.class, args);
        LOGGER.info("Project started successfully==========http://localhost:6003/");
    }

}
