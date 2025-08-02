package com.example.codesnippet.nacos.controller;

import com.example.codesnippet.nacos.diamond.MyConfig1;
import com.example.codesnippet.nacos.diamond.MyConfig2;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class HelloController {

    @Resource
    private MyConfig1 myConfig1;

    @Resource
    private MyConfig2 myConfig2;

    @GetMapping("config1")
    public String getCustomConfig(@RequestParam("key") String key) {
        return myConfig1.get(key);
    }
}
