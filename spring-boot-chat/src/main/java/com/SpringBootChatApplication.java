package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableAsync //开启异步任务
@EnableCaching
@MapperScan(basePackages = {"com.*.dao"})
@ServletComponentScan
public class SpringBootChatApplication {


    private final static Logger LOGGER = LoggerFactory.getLogger(SpringBootChatApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootChatApplication.class, args);
        LOGGER.info("Project started successfully==========http://localhost:6001/chat");
    }




}
