package com.example.codesnippet.manager;

import com.example.codesnippet.model.StockAlertEvent;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PublishService {

    private final RedissonClient redissonClient;

    public PublishService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public void publish(String channel, StockAlertEvent event) {
        RTopic topic = redissonClient.getTopic(channel);
        long subscribers = topic.publish(event);
        log.info("[PUBLISH] channel：[{}]，subscribers: [{}]", channel, subscribers);
    }
}
