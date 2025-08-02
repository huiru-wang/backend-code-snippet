package com.example.codesnippet.rocketmq.listener;

import com.example.codesnippet.rocketmq.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class RetryListenerDemo implements MessageService {

    @Override
    public String consumerGroup() {
        return "cg_code_snippet";
    }

    @Override
    public String topic() {
        return "code-snippet-rocketmq-topic";
    }

    @Override
    public String tags() {
        return "TagB";
    }

    @Override
    public void consume(MessageExt messageExt, ConsumeConcurrentlyContext context) {
        String message = new String(messageExt.getBody(), StandardCharsets.UTF_8);

        // 获取当前的重试次数
        int currentReconsumeTimes = messageExt.getReconsumeTimes();
        int delayTimeLevel = messageExt.getDelayTimeLevel();

        // 模拟重试
        if (currentReconsumeTimes < 5) {
            log.info("Retry: message: [{}] currentReconsumeTimes:[{}] delayTimeLevel:[{}]",
                    message, currentReconsumeTimes, delayTimeLevel);
            // TODO 通过context每次将消息的DelayLevel设为1，表示每次都是1s后重投，控制重投速率
            context.setDelayLevelWhenNextConsume(1);
            // 抛出BusinessException，标识重复消费
            throw new BusinessException("Retry");
        }
        // 消费成功
        log.info("Success: message: [{}] currentReconsumeTimes:[{}] delayTimeLevel:[{}]",
                message, currentReconsumeTimes, delayTimeLevel);
    }
}
