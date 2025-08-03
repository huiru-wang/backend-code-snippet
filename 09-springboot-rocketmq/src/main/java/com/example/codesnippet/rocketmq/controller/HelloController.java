package com.example.codesnippet.rocketmq.controller;

import com.example.codesnippet.rocketmq.manager.RocketMQProducer;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rocketmq")
public class HelloController {

    @Resource
    private RocketMQProducer rocketMQProducer;

    @PostMapping("/send")
    @ResponseBody
    public ResponseEntity<String> sendMessage(
            @RequestParam String topic,
            @RequestParam String tag,
            @RequestParam String msg) {
        boolean success = false;
        if (null == tag || tag.isEmpty()) {
            success = rocketMQProducer.syncSend(topic, msg);
        } else {
            success = rocketMQProducer.syncSend(topic, tag, msg);
        }
        return ResponseEntity.ok(success ? "success" : "fail");
    }
}
