package com.github.dylanz666.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : dylanz
 * @since : 12/06/2020
 */
@RestController
@RequestMapping("/api")
public class RedisPublishController {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //为了方便快速演示，我用了get API
    @GetMapping("/message")
    public String sendMessage(@RequestParam String message) {
        String channel = "channel:demo";
        stringRedisTemplate.convertAndSend(channel, message);
        return "success";
    }
}
