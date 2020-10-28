package com.github.dylanz666.controller;

import com.github.dylanz666.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    public @ResponseBody
    RedisStringCache redisStringCache(@RequestBody RedisStringCache redisStringCache) {
        String key = redisStringCache.getKey();
        String value = redisStringCache.getValue();

        stringRedisTemplate.opsForValue().set(key, value);
        String cachedValue = stringRedisTemplate.opsForValue().get(key);

        RedisStringCache cached = new RedisStringCache();
        cached.setKey(key);
        cached.setValue(cachedValue);
        return cached;
    }

    @PostMapping("/cache/hash")
    public @ResponseBody
    RedisHashCache redisHashCache(@RequestBody RedisHashCache redisHashCache) {
        String key = redisHashCache.getKey();
        String field = redisHashCache.getField();
        String value = redisHashCache.getValue();

        stringRedisTemplate.opsForHash().put(key, field, value);
        String cachedValue = Objects.requireNonNull(stringRedisTemplate.opsForHash().get(key, field)).toString();

        RedisHashCache cached = new RedisHashCache();
        cached.setKey(key);
        cached.setField(field);
        cached.setValue(cachedValue);
        return cached;
    }

    @PostMapping("/cache/list")
    public @ResponseBody
    RedisListCache redisListCache(@RequestBody RedisListCache redisListCache) {
        String key = redisListCache.getKey();
        List<String> list = redisListCache.getList();

        stringRedisTemplate.opsForList().leftPushAll(key, list);
        List<String> cachedList = stringRedisTemplate.opsForList().range(key, 0, -1);

        RedisListCache cached = new RedisListCache();
        cached.setKey(key);
        cached.setList(cachedList);
        return cached;
    }

    @PostMapping("/cache/set")
    public @ResponseBody
    RedisSetCache redisSetCache(@RequestBody RedisSetCache redisSetCache) {
        String key = redisSetCache.getKey();
        String[] sets = redisSetCache.getSets();

        stringRedisTemplate.opsForSet().add(key, sets);
        Set<String> cacheSets = stringRedisTemplate.opsForSet().members(key);

        RedisSetCache cached = new RedisSetCache();
        cached.setKey(key);
        assert cacheSets != null;
        cached.setSets(cacheSets.toArray(sets));
        return cached;
    }

    @PostMapping("/cache/zset")
    public @ResponseBody
    RedisZsetCache redisZsetCache(@RequestBody RedisZsetCache redisZsetCache) {
        String key = redisZsetCache.getKey();
        String value = redisZsetCache.getValue();
        double score = redisZsetCache.getScore();

        stringRedisTemplate.opsForZSet().add(key, value, score);

        Set<String> cachedZset = stringRedisTemplate.opsForZSet().rangeByScore(key, score, score);

        RedisZsetCache cached = new RedisZsetCache();
        cached.setKey(key);
        cached.setValue(value);
        cached.setScore(score);
        cached.setZset(cachedZset);
        return cached;
    }
}
