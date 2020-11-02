package com.github.dylanz666.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author : dylanz
 * @since : 11/02/2020
 */
@Configuration
@Aspect
@Order(1)
public class AOPConfig {
    @Around("@annotation(org.springframework.cache.annotation.Cacheable)")
    public Object cacheableAop(final ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }

    @Around("@annotation(org.springframework.cache.annotation.CachePut)")
    public Object cachePutAop(final ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }

    @Around("@annotation(org.springframework.cache.annotation.CacheEvict)")
    public Object cacheEvictAop(final ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }

    @Around("@annotation(org.springframework.cache.annotation.Caching)")
    public Object cachingAop(final ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }
}
