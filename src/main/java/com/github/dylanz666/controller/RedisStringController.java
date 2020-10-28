package com.github.dylanz666.controller;

import com.github.dylanz666.domain.RedisStringCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author : dylanz
 * @since : 10/28/2020
 */
@RestController
@RequestMapping("/api")
public class RedisStringController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/cache/string")
    public @ResponseBody RedisStringCache redisStringCache(@RequestBody RedisStringCache redisStringCache) {
        String key = redisStringCache.getKey();
        String value = redisStringCache.getValue();

        stringRedisTemplate.opsForValue().set(key, value);
        String cachedValue = stringRedisTemplate.opsForValue().get(key);

        RedisStringCache cached = new RedisStringCache();
        cached.setKey(key);
        cached.setValue(cachedValue);
        return cached;
    }
}
