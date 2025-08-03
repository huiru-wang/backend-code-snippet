package com.example.codesnippet.controller;

import com.example.codesnippet.manager.RedissonTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class HelloController {

    private final RedissonTestService redissonTestService;

    @Autowired
    public HelloController(RedissonTestService redissonTestService) {
        this.redissonTestService = redissonTestService;
    }

    @PostMapping("/lock")
    public boolean lock(@RequestParam String key) {
        return redissonTestService.lock(key);
    }

    @PostMapping("/tryLock")
    public boolean tryLock(@RequestParam String key) {
        return redissonTestService.tryLock(key);
    }
}
