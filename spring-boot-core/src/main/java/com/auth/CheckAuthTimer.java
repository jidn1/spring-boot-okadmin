package com.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/16 14:18
 * @Description:
 */
@Configuration
@EnableScheduling
@EnableAsync
public class CheckAuthTimer {

    @Scheduled(cron="0 0/10 * * * ?")
    public void check(){
        AuthCheckUtil.checkAuth();
    }
}
