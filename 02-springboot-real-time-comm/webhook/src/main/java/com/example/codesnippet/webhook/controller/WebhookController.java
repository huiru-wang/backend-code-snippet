package com.example.codesnippet.webhook.controller;

import com.example.codesnippet.webhook.model.ThirdServiceWebhookRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/")
public class WebhookController {

    /**
     * 第三方服务的webhook
     */
    @PostMapping("/webhook")
    public ResponseEntity<String> webhook(@RequestBody ThirdServiceWebhookRequest request) {
        // ip白名单

        // 限流

        // 请求签名验证

        // 根据业务唯一id 进行幂等

        // 业务处理

        // 约定的错误处理（特定响应执行重试等）

        return ResponseEntity.ok("");
    }
}
