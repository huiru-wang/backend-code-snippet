package com.example.codesnippet.websocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    // 处理客户端发送到/app/message的消息
    @MessageMapping("/message")
    @SendTo("/topic/messages") // 广播到所有订阅/topic/messages的客户端
    public String handleMessage(String message) {
        return "服务器回复: " + message;
    }
}