package com.example.codesnippet.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient(@Autowired RedisProperties redisProperties) {

        String host = redisProperties.getHost();
        int port = redisProperties.getPort();
        int database = redisProperties.getDatabase();

        // 创建配置对象
        Config config = new Config();

        // 1. 单机模式配置
        config.useSingleServer()
                .setAddress("redis://" + host + ":" + port)
                .setDatabase(database)
                .setConnectionMinimumIdleSize(5) // 最小空闲连接数
                .setConnectionPoolSize(10) // 连接池最大容量
                .setTimeout(3000) // 命令超时时间（毫秒）
                .setRetryAttempts(3) // 命令重试次数
                .setRetryInterval(1000); // 命令重试间隔（毫秒）

//        2. 集群模式配置（按需替换）
//        config.useClusterServers()
//              .addNodeAddress("redis://127.0.0.1:7001", "redis://127.0.0.1:7002")
//              .setScanInterval(2000) // 集群状态扫描间隔（毫秒）
//              .setMasterConnectionPoolSize(10)
//              .setSlaveConnectionPoolSize(5);

//        3. 哨兵模式配置（按需替换）
//        config.useSentinelServers()
//              .setMasterName("mymaster")
//              .addSentinelAddress("redis://127.0.0.1:26379", "redis://127.0.0.1:26380")
//              .setDatabase(0);

        return Redisson.create(config);
    }
}
