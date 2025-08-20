package com.example.codesnippet.subscribe;

import com.example.codesnippet.model.StockAlertEvent;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.listener.MessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StockAlertSubscriber implements MessageListener<StockAlertEvent> {

    @Override
    public void onMessage(CharSequence channel, StockAlertEvent event) {
        // 处理消息
        log.info("[LISTENER: StockAlertSubscriber] channel：[{}]，event: [{}]", channel, event);
    }
}