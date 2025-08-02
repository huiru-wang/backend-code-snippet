package com.example.codesnippet.rocketmq.listener;


import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(
        consumeThreadNumber = 20,
        consumeTimeout = 3000,
        consumerGroup = "cg-code-snippet",
        topic = "code-snippet-rocketmq-topic",
        selectorExpression = "TagA"
        // selectorExpression = "*"
)
public class SimpleListenerDemo implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("Topic Listener Receive: {}", message);
    }
}
