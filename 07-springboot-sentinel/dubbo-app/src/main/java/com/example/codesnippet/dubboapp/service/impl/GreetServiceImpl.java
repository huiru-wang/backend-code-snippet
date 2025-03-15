package com.example.codesnippet.dubboapp.service.impl;

import com.example.codesnippet.dubboapp.service.GreetService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(group = "demo", version = "1.0.0")
public class GreetServiceImpl implements GreetService {
    @Override
    public String greet(String message) {
        return "dubbo app [" + message + "]";
    }
}
