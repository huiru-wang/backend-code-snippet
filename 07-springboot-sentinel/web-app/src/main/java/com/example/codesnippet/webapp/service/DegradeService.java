package com.example.codesnippet.webapp.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class DegradeService {

    @SentinelResource("degrade-service")
    public String degrade() {
        log.info("degrade正常服务");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            log.warn("degrade slow RT");
        }
        return "success";
    }
}
