package com.example.codesnippet.rocketmq.listener;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.common.message.MessageExt;

public interface MessageService {

    /**
     * 消费者组
     */
    String consumerGroup();

    /**
     * 消费的Topic
     */
    String topic();

    /**
     * 消费的Tag
     */
    String tags();

    /**
     * 实现消费逻辑
     */
    void consume(MessageExt messageExt, ConsumeConcurrentlyContext context);
}
