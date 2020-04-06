package com.listener;

import com.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @Copyright: 正经吉
 * @author: StartupListener
 * @version: V1.0
 * @Date: 2020/4/6
 */
@WebListener
public class StartupListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(StartupListener.class);

    private ApplicationContext app;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        app = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
        SpringUtil.setApplication(app);


        logger.info("=======================【StartupListener Loaded Complete】==================================");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.error("===============【StartupListener Destroyed】====================");
    }

}
