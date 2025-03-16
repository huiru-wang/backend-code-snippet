package com.example.codesnippet.webapp.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.example.codesnippet.webapp.service.DegradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController("/")
public class HelloController {

    /**
     * 编码方式定义资源
     */
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

    /**
     * 注解方式定义资源
     */
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

    /**
     * 慢调用降级熔断case
     * 配置特定的慢调用策略，当慢调用比例达到一定数量，则降级或熔断
     */
    @Resource
    private DegradeService degradeService;

    @GetMapping("/degrade")
    public String degrade(@RequestParam("message") String message) {
        degradeService.degrade();
        return "hello [" + message + "]";
    }

    /**
     * 热点key的限流策略
     * <pre>
     *     C端流量一般使用用户维度的限流，如userId
     *     B端流量一般使用企业维度，如corpId
     * </pre>
     */
    @GetMapping("/hotKey")
    @SentinelResource(value = "hotKey-resource")
    public String hotKey(@RequestParam("userId") String userId) {
        return "hello [" + userId + "]";
    }
}
