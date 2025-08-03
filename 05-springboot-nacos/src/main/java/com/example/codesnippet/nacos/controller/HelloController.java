package com.example.codesnippet.nacos.controller;

import com.example.codesnippet.nacos.config.DynamicConfig;
import com.example.codesnippet.nacos.config.DynamicSwitch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nacos/config")
public class HelloController {

    private final DynamicConfig dynamicConfig;

    @Autowired
    public HelloController(DynamicConfig dynamicConfig) {
        this.dynamicConfig = dynamicConfig;
    }

    @GetMapping("/downgrade")
    public Boolean getCustomKey() {
        return DynamicSwitch.downgrade;
    }

    @GetMapping("/dynamic")
    @ResponseBody
    public DynamicConfig.CustomConfig getCustomConfig() {
        return dynamicConfig.getCustomConfig();
    }
}
