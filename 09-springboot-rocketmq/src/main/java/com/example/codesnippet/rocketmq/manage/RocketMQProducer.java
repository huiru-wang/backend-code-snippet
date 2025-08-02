package com.example.codesnippet.rocketmq.manage;

import jakarta.annotation.Resource;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * 1. 简单同步发送；syncSend
 * 2. 简单异步发送（不处理结果）convertAndSend
 * 3. 异步 + 回调；asyncSend + callback
 * 4. 单向发送，不处理结果：sendOneWay
 */
@Component
public class RocketMQProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public boolean syncSend(String topic, String msg) {
        Message<String> message = MessageBuilder.withPayload(msg).build();
        SendResult sendResult = rocketMQTemplate.syncSend(topic, message);
        return sendResult.getSendStatus() == SendStatus.SEND_OK;
    }

    public boolean syncSend(String topic, String tag, String msg) {
        String destination = topic + ":" + tag;
        Message<String> message = MessageBuilder.withPayload(msg).build();
        SendResult sendResult = rocketMQTemplate.syncSend(destination, message);
        return sendResult.getSendStatus() == SendStatus.SEND_OK;
    }

    /**
     * 异步发送，不阻塞当前线程，发送在后台完成
     */
    public void asyncSend(String topic, String tag, String msg) {
        String destination = topic + ":" + tag;
        Message<String> message = MessageBuilder.withPayload(msg).build();
        rocketMQTemplate.convertAndSend(destination, message);
    }

    /**
     * 异步发送，注册回调，后台发送失败时执行callback
     */
    public void asyncSendWithCallback(String topic, String tag, String msg) {
        String destination = topic + ":" + tag;
        Message<String> message = MessageBuilder.withPayload(msg).build();
        rocketMQTemplate.asyncSend(destination, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("sendResult:" + sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                // 兜底操作：落库、告警、重试等
                System.out.println("onException:" + throwable);
            }
        });
    }

    /**
     * 单向发送，不阻塞、不处理结果，类似UDP
     */
    public void sendOneWay(String topic, String tag, String msg) {
        String destination = topic + ":" + tag;
        Message<String> message = MessageBuilder.withPayload(msg).build();
        rocketMQTemplate.sendOneWay(destination, message);
    }
}
