package com.example.codesnippet.dubboprovider;

import com.example.codesnippet.dubboprovider.service.impl.DemoServiceImpl;
import com.example.codesnippet.service.DemoService;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.utils.NamedThreadFactory;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ProviderConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.bootstrap.builders.ServiceBuilder;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@EnableDubbo
@SpringBootApplication
public class DubboProviderApplication {

    public static void main(String[] args) {

        SpringApplication.run(DubboProviderApplication.class, args);

        // ========================== Dubbo 原生 API启动 ==========================

//        // 应用配置
//        ApplicationConfig applicationConfig = new ApplicationConfig();
//        applicationConfig.setName("dubbo-app-1");
//        applicationConfig.setShutwait("10s");
//        applicationConfig.setQosEnable(true);
//        applicationConfig.setQosPort(22222);
//
//        // 协议
//        ProtocolConfig protocolConfig = new ProtocolConfig(CommonConstants.TRIPLE, -1);
//
//        // 注册中心
//        RegistryConfig registryConfig = new RegistryConfig();
//        registryConfig.setAddress("nacos://127.0.0.1:8848?namespace=00079ffa-7e3e-4d31-861e-a807d1cdeaef");
//        registryConfig.setRegisterMode("instance");
//
//        // 服务注册
//        ServiceConfig<Object> serviceConfig = ServiceBuilder.newBuilder()
//                .interfaceClass(DemoService.class).ref(new DemoServiceImpl())
//                .group("demo-group")
//                .version("1.0.0")
//                .timeout(5000)
//                .executes(10)                           // Provider接收的最大并发调用
//                .executor(buildServiceExecutor())       // 独立线程池
//                .async(false)
//                .filter("logFilter")
//                .build();
//        // Provider全局配置
//        ProviderConfig providerConfig = new ProviderConfig();
//        providerConfig.setAsync(false);
//        providerConfig.setParameters(Map.of("enable-timeout-countdown", "true"));
//
//        // 启动Dubbo
//        DubboBootstrap.getInstance()
//                .application(applicationConfig)
//                .protocol(protocolConfig)
//                .registry(registryConfig)
//                .service(serviceConfig)
//                .provider(providerConfig)
//                .start()
//                .await();
    }

    private static Executor buildServiceExecutor() {
        NamedThreadFactory namedThreadFactory = new NamedThreadFactory("dubbo-demo-provider-executor");
        return Executors.newFixedThreadPool(10, namedThreadFactory);
    }
}
