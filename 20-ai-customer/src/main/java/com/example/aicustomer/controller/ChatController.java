package com.example.aicustomer.controller;

import com.example.aicustomer.model.CustomerChatRequest;
import com.example.aicustomer.model.CustomerChatResponse;
import com.example.aicustomer.model.ServiceResult;
import com.example.aicustomer.service.ai.AICustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/ai-customer")
public class ChatController {

    private final AICustomerService aiCustomerService;

    public ChatController(AICustomerService aiCustomerService) {
        this.aiCustomerService = aiCustomerService;
    }

    @PostMapping("/chat/sync")
    public ServiceResult<CustomerChatResponse> syncChat(@RequestBody CustomerChatRequest customerChatRequest) {
        String chatResponseText = aiCustomerService.chat(customerChatRequest.getMessage());
        log.info("Chat Token: {}", chatResponseText);
        return ServiceResult.success(CustomerChatResponse.builder().content(chatResponseText).build());
    }

    @PostMapping("/chat/stream")
    public ResponseEntity<StreamingResponseBody> streamChat(@RequestBody CustomerChatRequest customerChatRequest) {
        String message = customerChatRequest.getMessage();
        log.info("Chat Call Message: {}", message);
        StreamingResponseBody streamingResponseBody = aiCustomerService.streamingChat(message);
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN) // 或 application/octet-stream
                .body(streamingResponseBody);
    }
}
