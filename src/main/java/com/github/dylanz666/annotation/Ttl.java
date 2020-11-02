package com.github.dylanz666.annotation;

import com.github.dylanz666.constant.DurationUnitEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : dylanz
 * @since : 11/01/2020
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Ttl {
    //设置缓存有效时间，单位为秒，默认60秒
    long duration() default 60;

    //时间单位，默认为秒，SECOND，可选的有：NANO,MILLI,SECOND,MINUTE,HOUR,DAY
    DurationUnitEnum unit() default DurationUnitEnum.SECOND;
}
