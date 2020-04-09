package com.common.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class MyAdapter extends WebMvcConfigurerAdapter {

    @Autowired
    private BaseInterceptor baseInterceptor;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }

    //配置一个静态文件的路径 否则css和js无法使用，虽然默认的静态资源是放在static下，但是没有配置里面的文件夹
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
    @Bean
    public WebMvcConfigurerAdapter WebMvcConfigurerAdapter() {
        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/pic/**").addResourceLocations("file:D:/ifeng/");
                super.addResourceHandlers(registry);
            }
        };
        return adapter;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册TestInterceptor拦截器
        registry.addInterceptor(baseInterceptor);
    }
}
