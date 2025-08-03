package com.example.codesnippet.rocketmq.annotation;

import org.apache.rocketmq.common.consumer.ConsumeFromWhere;

import java.lang.annotation.*;

/**
 * 自定义注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomRocketMQListener {

    /**
     * 消费者组名
     */
    String consumerGroup();

    /**
     * 主题名称
     */
    String topic();

    /**
     * 标签过滤表达式，默认为 "*" 表示接收所有标签的消息
     */
    String tags() default "*";

    /**
     * 名称服务器地址
     */
    String nameServer() default "";

    /**
     * 从哪里开始消费
     */
    ConsumeFromWhere consumeFromWhere() default ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET;

    /**
     * 批量拉取消息大小
     */
    int pullBatchSize() default 32;

    /**
     * 消费线程数最小值
     */
    int consumeThreadMin() default 20;

    /**
     * 消费线程数最大值
     */
    int consumeThreadMax() default 20;

    /**
     * 消费超时时间（分钟）
     */
    long consumeTimeout() default 15;

    /**
     * 最大重试次数
     */
    int maxReconsumeTimes() default 3;

    /**
     * 拉取消息时间间隔（毫秒）
     */
    long pullInterval() default 0;

    /**
     * 消费队列长度阈值
     */
    int pullThresholdForQueue() default 1000;

    /**
     * 单次批量消费的最大消息数
     */
    int consumeMessageBatchMaxSize() default 1;

    /**
     * 是否启用顺序消费
     */
    boolean orderly() default false;

    Boolean test();
}
