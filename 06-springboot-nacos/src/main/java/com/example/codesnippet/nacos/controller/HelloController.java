package com.example.codesnippet.nacos.controller;

import com.example.codesnippet.nacos.config.CustomConfig;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class HelloController {

    @Resource
    private CustomConfig customConfig;

    @GetMapping("config")
    public String getCustomConfig(@RequestParam("key") String key) {
        return customConfig.get(key);
    }
}
