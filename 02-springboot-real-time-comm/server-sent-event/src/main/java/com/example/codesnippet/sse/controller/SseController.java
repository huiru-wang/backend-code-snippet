package com.example.codesnippet.sse.controller;

import com.example.codesnippet.sse.model.ChatRequest;
import com.example.codesnippet.sse.service.SseContentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController("/")
public class SseController {

    @Resource
    private SseContentService sseContentService;

    // 客户端订阅入口
    @GetMapping("/subscribe")
    @CrossOrigin(value = "*")
    public SseEmitter subscribe(@RequestParam String userId) {
        return sseContentService.subscribe(userId);
    }

    @PostMapping("/chat")
    @CrossOrigin(value = "*")
    public ResponseEntity<String> chat(@RequestBody ChatRequest chatRequest) {
        String userId = chatRequest.getUserId();
        String content = chatRequest.getContent();
        sseContentService.submitChat(userId, content);
        return ResponseEntity.ok("Chat Receive Success");
    }

}
