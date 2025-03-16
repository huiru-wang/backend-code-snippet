package com.example.codesnippet.clustermode.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/")
public class HelloController {

    @GetMapping("cluster")
    @SentinelResource("cluster-resource")
    public String clusterHello(@RequestParam("id") String id) {
        return "cluster hello [" + id + "]";
    }
}
