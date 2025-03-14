package com.example.codesnippet.redis;

import com.example.codesnippet.util.CacheUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.util.AssertionErrors;

@Slf4j
@SpringBootTest
public class StringOpsTest {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ValueOperations<String, String> valueOperations;

    @Test
    public void testStringOpsHello() {
        String key = CacheUtils.buildKey("hello", "string");
        Boolean delete = redisTemplate.delete(key);
        log.info("key:{}, delete:{}", key, delete);

        String value = "hello";
        valueOperations.set(key, value);

        String result = valueOperations.get(key);
        AssertionErrors.assertEquals("ValueOps Fail", value, result);
        log.info("StringOps: [key:{}, value:{}]", key, result);
    }

}
