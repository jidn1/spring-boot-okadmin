package com.common.aop;

import com.common.utils.IpUtils;
import com.exception.CusException;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import com.google.common.util.concurrent.RateLimiter;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2020/1/3 12:39
 * @Description: 限流 Ip 令牌桶
 */
@Aspect
@Configuration
public class LimitAspect {

    /**
     * 根据IP分不同的令牌桶, 每天自动清理缓存
     */
    private static LoadingCache<String, RateLimiter> caches = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<String, RateLimiter>() {
                @Override
                public RateLimiter load(String key) {
                    // 新的IP初始化 每秒只发出5个令牌
                    return RateLimiter.create(5);
                }
            });

    /**
     * 切点  限流
     */
    @Pointcut("@annotation(com.common.aop.ServiceLimit)")
    public void ServiceAspect() {

    }

    @Around("ServiceAspect()")
    public  Object around(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ServiceLimit limitAnnotation = method.getAnnotation(ServiceLimit.class);
        ServiceLimit.LimitType limitType = limitAnnotation.limitType();
        String key = limitAnnotation.key();
        Object obj;
        try {
            if(limitType.equals(ServiceLimit.LimitType.IP)){
                key = IpUtils.getIpAddr();
            }
            RateLimiter rateLimiter = caches.get(key);
            Boolean flag = rateLimiter.tryAcquire();
            if(flag){
                obj = joinPoint.proceed();
            }else{
                throw new CusException("小同志，你访问的太频繁了");
            }
        } catch (Throwable e) {
            throw new CusException("小同志，你访问的太频繁了");
        }
        return obj;
    }
}
