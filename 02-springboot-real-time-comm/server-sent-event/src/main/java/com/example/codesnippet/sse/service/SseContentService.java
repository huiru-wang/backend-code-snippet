package com.example.codesnippet.sse.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@EnableScheduling
public class SseContentService implements InitializingBean {

    // 存储所有客户端的SseEmitter（线程安全）
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    // 模拟事件推送
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    // 心跳维护队列
    private ThreadPoolTaskScheduler taskScheduler;

    @Override
    public void afterPropertiesSet() {
        taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        taskScheduler.initialize();
    }

    /**
     * SSE订阅
     */
    public SseEmitter subscribe(String userId) {
        if (emitters.containsKey(userId)) {
            return emitters.get(userId);
        }
        log.info("[{}] SSE Subscribe Start", userId);
        // 为此次订阅创建一个SseEmitter
        SseEmitter emitter = new SseEmitter(60_000L);
        // 注册回调：连接完成或超时时移除emitter
        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> {
            emitters.remove(userId);
            log.info("[{}] session timeout", userId);
        });
        emitters.put(userId, emitter);
        log.info("[{}] SSE Subscribe Success", userId);

        // 心跳机制防止超时
        taskScheduler.scheduleAtFixedRate(() -> heartbeat(userId), Duration.ofSeconds(10));

        return emitter;
    }

    /**
     * 模拟聊天，5s后返回内容
     */
    public void submitChat(String userId, String content) {
        // 模拟5s后，推送消息
        executor.schedule(() -> {
            String serverContent = String.format("[%s] send question: %s \n Server Response: [%s]", userId, content, "SSE Content Response");
            pushData(userId, serverContent);
        }, 5L, TimeUnit.SECONDS);
    }

    /**
     * 模拟订阅后的消息推送
     */
    public void pushData(String userId, String data) {
        SseEmitter emitter = emitters.get(userId);
        if (emitter == null) {
            log.info("[{}] SSE Session is Closed", userId);
            return;
        }
        try {
            SseEmitter.SseEventBuilder sseEventBuilder = SseEmitter.event()
                    .id(String.valueOf(System.currentTimeMillis()))
                    .name("message")         // 标记事件类型
                    .data(data);
            emitter.send(sseEventBuilder);
            log.info("[{}] SSE Content Delivery Success", userId);
        } catch (IOException e) {
            emitters.remove(userId); // 发送失败时移除
        }
    }

    /**
     * 心跳机制，防止超时，超时应该由客户端主动断联
     * 需要续期才能防止超时
     */
    public void heartbeat(String userId) {
        SseEmitter emitter = emitters.get(userId);
        if (emitter == null) {
            log.info("[{}] SSE Session is Closed", userId);
            return;
        }
        try {
            // timeout续期

            SseEmitter.SseEventBuilder sseEventBuilder = SseEmitter.event()
                    .id(String.valueOf(System.currentTimeMillis()))
                    .name("heartbeat")         // 标记事件类型
                    .data("");
            emitter.send(sseEventBuilder);
            log.info("[{}] SSE heartbeat", userId);
        } catch (IOException e) {
            emitters.remove(userId); // 发送失败时移除
        }
    }


}
