package com.example.codesnippet.redis;

import com.example.codesnippet.util.CacheUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import java.util.Random;
import java.util.Set;
@Slf4j
@SpringBootTest
public class SetOpsTest {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private SetOperations<String, String> setOperations;

    @Test
    public void testSetOpsHello() {
        String key = CacheUtils.buildKey("hello", "set");
        Boolean delete = redisTemplate.delete(key);
        log.info("key:{}, delete:{}", key, delete);

        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int next = random.nextInt(10000);
            setOperations.add(key, String.valueOf(next));
        }

        Set<String> members = setOperations.members(key);
        log.info("ListOps: [members:{}]", members);
    }
}
