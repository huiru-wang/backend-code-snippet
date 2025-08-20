package com.example.codesnippet.config;

import com.example.codesnippet.constants.RedisEventTopic;
import com.example.codesnippet.model.StockAlertEvent;
import com.example.codesnippet.subscribe.StockAlertSubscriber;
import com.example.codesnippet.subscribe.StockPriceSubscriber;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * 注册订阅者
 */
@Slf4j
@Configuration
public class PubSubscribeConfig {

    private final RedissonClient redissonClient;

    private final StockAlertSubscriber stockAlertSubscriber;

    private final StockPriceSubscriber stockPriceSubscriber;

    public PubSubscribeConfig(RedissonClient redissonClient,
                              StockAlertSubscriber stockAlertSubscriber,
                              StockPriceSubscriber stockPriceSubscriber) {
        this.redissonClient = redissonClient;
        this.stockAlertSubscriber = stockAlertSubscriber;
        this.stockPriceSubscriber = stockPriceSubscriber;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeSubscribers() {
        // 为股票预警频道注册订阅者
        RTopic stockAlertTopic = redissonClient.getTopic(RedisEventTopic.STOCK_ALERT_CHANNEL);

        // 多个订阅者
        stockAlertTopic.addListener(StockAlertEvent.class, stockAlertSubscriber);
        stockAlertTopic.addListener(StockAlertEvent.class, stockPriceSubscriber);

        log.info("[REGISTRY] {}", RedisEventTopic.STOCK_ALERT_CHANNEL);
    }
}
