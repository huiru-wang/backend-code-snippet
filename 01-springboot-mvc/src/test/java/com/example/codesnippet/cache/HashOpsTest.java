package com.example.codesnippet.cache;

import com.example.codesnippet.util.CacheUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.util.Map;
@Slf4j
@SpringBootTest
public class HashOpsTest {


    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private HashOperations<String, String, Object> hashOperations;

    @Test
    public void testHashOpsHello() {
        String key = CacheUtils.buildKey("hello", "hash");
        Boolean delete = redisTemplate.delete(key);
        log.info("key:{}, delete:{}", key, delete);

        String field = "field-";
        String value = "value-";
        for (int i = 0; i < 5; i++) {
            hashOperations.put(key, field + i, value + i);
        }

        Map<String, Object> entries = hashOperations.entries(key);
        for (int i = 0; i < 5; i++) {
            String fieldItem = field + i;
            String valueItem = (String) entries.get(fieldItem);
            Assert.isTrue((value + i).equals(valueItem), "HashOps Fail");
            log.info("HashOps: [field:{}, value:{}]", fieldItem, valueItem);
        }
    }
}
