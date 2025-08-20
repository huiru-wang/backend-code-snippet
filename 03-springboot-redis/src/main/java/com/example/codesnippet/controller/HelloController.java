package com.example.codesnippet.controller;

import com.example.codesnippet.constants.RedisEventTopic;
import com.example.codesnippet.manager.LockService;
import com.example.codesnippet.manager.PublishService;
import com.example.codesnippet.model.StockAlertEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class HelloController {

    private final LockService lockService;

    private final PublishService publishService;

    @Autowired
    public HelloController(LockService lockService, PublishService publishService) {
        this.lockService = lockService;
        this.publishService = publishService;
    }

    @PostMapping("/lock")
    public boolean lock(@RequestParam String key) {
        return lockService.lock(key);
    }

    @PostMapping("/tryLock")
    public boolean tryLock(@RequestParam String key) {
        return lockService.tryLock(key);
    }

    @PostMapping("/publish")
    public void publishStockEvent(@RequestBody StockAlertEvent event) {
        publishService.publish(RedisEventTopic.STOCK_ALERT_CHANNEL, event);
    }
}
