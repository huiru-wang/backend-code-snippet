package com.example.codesnippet.dubboconsumer.controller;

import com.example.codesnippet.model.DemoMessage;
import com.example.codesnippet.model.ServiceResult;
import com.example.codesnippet.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/")
public class HelloController {

    @DubboReference(
            group = "demo", version = "1.0.0", actives = 10,
            cluster = "failover", retries = 3
    )
    private DemoService demoService;

    @GetMapping("/hello")
    public ServiceResult<DemoMessage> hello(@RequestParam("message") String message) {
        ServiceResult<DemoMessage> echoResult = demoService.echo(message);
        if (!echoResult.isSuccess()) {
            return ServiceResult.fail("400", "echo service fail");
        }
        String traceId = RpcContext.getServerAttachment().getAttachment("trace-id");
        log.info("Request Success TraceId: {}", traceId);
        return ServiceResult.success(echoResult.getData());
    }
}
