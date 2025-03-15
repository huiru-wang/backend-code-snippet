package com.example.codesnippet.dubboprovider.service.impl;

import com.example.codesnippet.dubbo.model.DemoMessage;
import com.example.codesnippet.dubbo.model.ServiceResult;
import com.example.codesnippet.dubbo.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.UUID;

@Slf4j
@DubboService(group = "demo", version = "1.0.0", executor = "demoExecutor", executes = 10)
public class DemoServiceImpl implements DemoService {
    @Override
    public ServiceResult<DemoMessage> echo(String content) {
        log.info("dubbo-provider receive message: {}", content);
        DemoMessage demoMessage = new DemoMessage();
        demoMessage.setContent(content);
        demoMessage.setId(UUID.randomUUID().toString());
        return ServiceResult.success(demoMessage);
    }
}
