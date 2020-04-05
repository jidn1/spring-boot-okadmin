package com.chat;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync //开启异步任务
@ServletComponentScan
@MapperScan({"com.chat.dao"})
public class SpringBootChatApplication {


    private final static Logger LOGGER = LoggerFactory.getLogger(SpringBootChatApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootChatApplication.class, args);
        LOGGER.info("Project started successfully==========http://localhost:6002/index");
    }

}
