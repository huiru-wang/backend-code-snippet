package com.example.codesnippet.webapp.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        try (Entry entry = SphU.entry("hello-resource")) {
            // 被保护的逻辑
            return "hello world";
        } catch (BlockException ex) {
            // 处理被流控的逻辑
            return "Sentinel block";
        }
    }

    @GetMapping("/greet")
    @SentinelResource(value = "greet-resource", blockHandler = "greetBlockHandler", fallback = "greetFallback")
    public String greet(@RequestParam("message") String message) {
        Assert.isTrue(!"Test".equals(message), "Test Not Accept");
        return "hello [" + message + "]";
    }

    /**
     * 当触发流控规则、降级规则，会走到BlockHandler
     * message；用于保持和原方法一样的入参
     * BlockException接受原方法的降级、流控触发的异常
     */
    public String greetBlockHandler(String message, BlockException ex) {
        ex.printStackTrace();
        return "Sentinel block [" + message + "]";
    }

    /**
     * 当原方法抛出异常，而非降级、流控规则，则会走到：fallback
     * message；用于保持和原方法一样的入参
     * Throwable接受原方法的异常
     */
    public String greetFallback(String message, Throwable e) {
        e.printStackTrace();
        return "Sentinel fallback [" + message + "]";
    }
}
