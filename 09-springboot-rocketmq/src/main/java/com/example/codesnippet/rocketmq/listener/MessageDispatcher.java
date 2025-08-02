package com.example.codesnippet.rocketmq.listener;

import com.example.codesnippet.rocketmq.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class MessageDispatcher {

    @Value("${rocketmq.consumer.max.retry.times}")
    private int MAX_RETRY_TIMES = 10;

    @Value("${rocketmq.name-server}")
    private String NAMESERVER_ADDR="192.168.1.3:9876";

    @Autowired
    public MessageDispatcher(List<MessageService> messageServiceList) throws MQClientException {
        if (CollectionUtils.isEmpty(messageServiceList)) {
            return;
        }
        for (MessageService messageService : messageServiceList) {
            String consumerGroup = messageService.consumerGroup();
            String topic = messageService.topic();
            String tags = messageService.tags();

            // 初始化并开启消费者监听
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
            consumer.setConsumerGroup(consumerGroup);
            consumer.setNamesrvAddr(NAMESERVER_ADDR);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            consumer.subscribe(topic, MessageSelector.byTag(tags));
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeConcurrentlyContext context) {
                    if (CollectionUtils.isEmpty(messageExtList)) {
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    for (MessageExt messageExt : messageExtList) {
                        if (null == messageExt) {
                            continue;
                        }
                        Throwable throwable = null;
                        int reconsumeTimes = messageExt.getReconsumeTimes();
                        if (reconsumeTimes > MAX_RETRY_TIMES) {
                            // 超出最大重试次数，不再重复消费
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }
                        try {
                            messageService.consume(messageExt, context);
                        } catch (BusinessException e) {
                            // TODO 业务逻辑异常，执行重试
                            throwable = e;
                            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                        } catch (Exception e) {
                            // TODO 未知异常，日志监控，落库等根据业务进行处理
                            throwable = e;
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        } finally {
                            // TODO 记录日志
                            if (null != throwable) {
                                log.error(throwable.getMessage(), throwable);
                            }
                        }
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
        }
    }
}
