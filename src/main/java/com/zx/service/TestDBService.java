package com.zx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TestDBService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public String test(String value){

        String key = "redPacket:test";

        redisTemplate.opsForValue().set(key,value);
        redisTemplate.expire(key, 1000000, TimeUnit.SECONDS);
        String result =  redisTemplate.opsForValue().get(key);
        return result;
    }

}
