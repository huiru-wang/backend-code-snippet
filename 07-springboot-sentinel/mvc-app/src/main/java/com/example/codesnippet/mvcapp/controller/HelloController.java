package com.example.codesnippet.mvcapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/")
public class HelloController {

    @GetMapping("/hi")
    public String hi(@RequestParam("message") String message) {
        Assert.isTrue(!"Test".equals(message), "Test Not Accept");
        return "hello [" + message + "]";
    }
}
