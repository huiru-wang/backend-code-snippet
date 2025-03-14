package com.example.codesnippet.redis;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@SpringBootTest
public class RedisTemplateTest {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public void redisStringOps() {
        redisTemplate.opsForValue().set("test", "test");
    }
}
