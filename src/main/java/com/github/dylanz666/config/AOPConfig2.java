package com.github.dylanz666.config;

import com.github.dylanz666.annotation.Ttl;
import com.github.dylanz666.constant.DurationUnitEnum;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.Duration;

/**
 * @author : dylanz
 * @since : 11/01/2020
 */
@Configuration
@Aspect
@Order(2)
public class AOPConfig2 {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @After("@annotation(com.github.dylanz666.annotation.Ttl)")
    public void simpleAop2(final JoinPoint joinPoint) throws Throwable {
        //获取一些基础信息
        String methodName = joinPoint.getSignature().getName();
        Class<?> targetClass = joinPoint.getTarget().getClass();
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        Method objMethod = targetClass.getMethod(methodName, parameterTypes);

        //获取想要设置的缓存有效时长
        Ttl cacheDuration = objMethod.getDeclaredAnnotation(Ttl.class);
        long duration = cacheDuration.duration();
        DurationUnitEnum unit = cacheDuration.unit();

        //获取缓存注解的values/cacheNames和key
        String[] values = getValues(objMethod);
        String key = getKey(objMethod);

        //准备好变量名与变量值的上下文绑定
        Parameter[] parameters = objMethod.getParameters();
        Object[] args = joinPoint.getArgs();
        StandardEvaluationContext ctx = new StandardEvaluationContext();
        for (int i = 0; i < parameters.length; i++) {
            ctx.setVariable(parameters[i].getName(), args[i]);
        }

        //获取真实的key，而不是变量形式的key
        ExpressionParser expressionParser = new SpelExpressionParser();
        Expression expression = expressionParser.parseExpression(key);
        String realKey = expression.getValue(ctx, String.class);

        //设置缓存生效时长，即过期时间
        setExpiration(values, realKey, duration, unit);
    }

    private void setExpiration(String[] values, String realKey, long duration, DurationUnitEnum unit) throws InterruptedException {
        Duration expiration;
        if (unit == DurationUnitEnum.NANO) {
            expiration = Duration.ofNanos(duration);
        } else if (unit == DurationUnitEnum.MILLI) {
            expiration = Duration.ofMillis(duration);
        } else if (unit == DurationUnitEnum.MINUTE) {
            expiration = Duration.ofMinutes(duration);
        } else if (unit == DurationUnitEnum.HOUR) {
            expiration = Duration.ofHours(duration);
        } else if (unit == DurationUnitEnum.DAY) {
            expiration = Duration.ofDays(duration);
        } else {
            expiration = Duration.ofSeconds(duration);
        }
        for (String value : values) {
            String redisKey = value + "::" + realKey;
            stringRedisTemplate.expire(redisKey, expiration);
        }
    }

    private String[] getValues(Method objMethod) {
        Cacheable cacheable = objMethod.getDeclaredAnnotation(Cacheable.class);
        CachePut cachePut = objMethod.getDeclaredAnnotation(CachePut.class);
        CacheEvict cacheEvict = objMethod.getDeclaredAnnotation(CacheEvict.class);
        //value
        if (cacheable != null && cacheable.value().length > 0) {
            return cacheable.value();
        }
        if (cachePut != null && cachePut.value().length > 0) {
            return cachePut.value();
        }
        if (cacheEvict != null && cacheEvict.value().length > 0) {
            return cacheEvict.value();
        }
        //cacheNames
        if (cacheable != null && cacheable.cacheNames().length > 0) {
            return cacheable.cacheNames();
        }
        if (cachePut != null && cachePut.cacheNames().length > 0) {
            return cachePut.cacheNames();
        }
        if (cacheEvict != null && cacheEvict.cacheNames().length > 0) {
            return cacheEvict.cacheNames();
        }
        return new String[]{};
    }

    private String getKey(Method objMethod) {
        Cacheable cacheable = objMethod.getDeclaredAnnotation(Cacheable.class);
        CachePut cachePut = objMethod.getDeclaredAnnotation(CachePut.class);
        CacheEvict cacheEvict = objMethod.getDeclaredAnnotation(CacheEvict.class);
        if (cacheable != null && !cacheable.key().equals("")) {
            return cacheable.key();
        }
        if (cachePut != null && !cachePut.key().equals("")) {
            return cachePut.key();
        }
        if (cacheEvict != null && !cacheEvict.key().equals("")) {
            return cacheEvict.key();
        }
        return "";
    }
}
