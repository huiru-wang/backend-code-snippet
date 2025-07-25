package com.example.codesnippet.kafka;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.example.codesnippet.kafka.model.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@SpringBootTest
public class KafkaProducerSimpleAPI {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    String topicName = "snippet";

    String messageKey = "order-key";

    String messageValue = null;

    @BeforeEach
    public void init() {
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setOrderId(IdUtil.simpleUUID());
        orderMessage.setUserId(IdUtil.simpleUUID());
        orderMessage.setOrderInfo("test-order");
        orderMessage.setOrderTime(LocalDateTime.now());
        messageValue = JSON.toJSONString(orderMessage);
    }

    /**
     * send 为异步发送
     */
    @Test
    public void async_send() {
        kafkaTemplate.send(topicName, 1, messageKey, messageValue); // 1、指定partition
        // kafkaTemplate.send(topicName, messageKey, messageValue); // 2、默认hash分区
        // kafkaTemplate.send(topicName, messageValue); // 3、选择粘性分区，尽可能放在最多的batch中，满了能尽快发送
        // 4、自定义分区实现Partitioner接口
    }

    /**
     * send 本为异步发送，如果要同步，使用Future.get()阻塞等待获取结果；
     */
    @Test
    public void sync_send() {
        SendResult<String, String> sendResult = null;
        try {
            sendResult = kafkaTemplate.send(topicName, messageKey, messageValue).get();
        } catch (Exception e) {
            log.error("send fail: ", e);
        }
        log.info("sendResult: {}", sendResult);
    }

    @Test
    public void sync_send_record() {
        //  kafka 提供的消息封装类
        ProducerRecord<String, String> record = new ProducerRecord<>(topicName, messageKey, messageValue);
        CompletableFuture<SendResult<String, String>> completableFuture = kafkaTemplate.send(record);
        SendResult<String, String> sendResult = null;
        try {
            sendResult = completableFuture.get();
        } catch (Exception e) {
            log.error("send fail: ", e);
        }
        log.info("sendResult: {}", sendResult);
    }
}
