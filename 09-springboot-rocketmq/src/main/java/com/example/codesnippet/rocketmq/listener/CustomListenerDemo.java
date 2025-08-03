package com.example.codesnippet.rocketmq.listener;

import com.example.codesnippet.rocketmq.annotation.CustomRocketMQListener;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;

// 自定义注解
@CustomRocketMQListener(
        consumerGroup="",
        topic = "",
        tags = "",
        nameServer = "",
        consumeFromWhere = ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET
)
public class CustomListenerDemo {
}
