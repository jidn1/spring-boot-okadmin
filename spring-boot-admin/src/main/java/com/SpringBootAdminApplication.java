package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@MapperScan(basePackages = {"com.*.dao"})
@EnableCaching
@ServletComponentScan
public class SpringBootAdminApplication {

    private final static Logger LOGGER = LoggerFactory.getLogger(SpringBootAdminApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAdminApplication.class, args);
        LOGGER.info("Project started successfully==========http://localhost:6002/index");
    }



}
