package com.listener;

import com.hothub.timer.CrawlerTimer;
import com.movie.timer.CrawlerPornTimer;
import com.oauth.service.ConfigService;
import com.oauth.service.SystemUserService;
import com.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/17 14:56
 * @Description:
 */

@WebListener
public class StartupListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(StartupListener.class);

    private ApplicationContext app;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        app = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
        SpringUtil.setApplication(app);

        ConfigService configService = (ConfigService)app.getBean("configService");
        configService.initRedisConfig();

        SystemUserService systemUserService = (SystemUserService)app.getBean("systemUserService");
        systemUserService.initRedis();


//        CrawlerTimer.initCnBlogTimer();
//        CrawlerTimer.initBaiDuTimer();
//        CrawlerTimer.initZhiHuTimer();
//        CrawlerTimer.initBbsTimer();

        //CrawlerPornTimer.initPornTimer();

        logger.info("=======================【StartupListener Loaded Complete】==================================");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.error("===============【StartupListener Destroyed】====================");
    }
}
