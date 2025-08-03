package com.example.codesnippet.manager;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedissonTestService {

    private final RedissonClient redissonClient;

    public RedissonTestService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 阻塞式分布式锁
     */
    public boolean lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        // 一直阻塞，直到获取锁
        // leaseTime：锁的TTL，默认30s
        lock.lock(5, TimeUnit.SECONDS);
        try {
            // 获取锁成功，执行业务逻辑
            log.info("Get Lock Success");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            return true;
        } finally {
            lock.unlock();
        }
        return true;
    }

    /**
     * 非阻塞锁
     */
    public boolean tryLock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        boolean res = false;
        try {
            // tryLock(等待时长，持锁时长，时间单位);
            // 等待时长为0，抢锁失败立即返回false
            // 持锁时间为0，则为默认值30s，并自动开启看门狗续期
            res = lock.tryLock(0, 0, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("Lock Interrupted: {}", e.getMessage());
        }
        if (!res) {
            log.info("Get Lock Failed");
            return false;
        }
        try {
            // 获取锁成功，执行业务逻辑
            log.info("Get Lock Success: {}", Thread.currentThread().getId());
            // 等待50s，测试看门狗续期，锁的TTL实际每到20s时，会被续期为30s，TTL每消耗1/3就会进行续期
            TimeUnit.SECONDS.sleep(50);
        } catch (InterruptedException e) {
            // 获取锁的过程中被中断
            return false;
        } finally {
            lock.unlock();
            log.info("UnLocked");
        }
        return true;
    }

    /**
     * 上面的锁默认都是非公平锁，效率更高。
     * 如果业务上需要公平锁，redisson也可以支持
     */
    public boolean fairLock(String lockKey) throws InterruptedException {
        RLock fairLock = redissonClient.getFairLock(lockKey);
        boolean res = fairLock.tryLock(0, 10, TimeUnit.SECONDS);
        if (!res) {
            return false;
        }
        try {
            log.info("Get fairLock Success");
            TimeUnit.SECONDS.sleep(5);
        }catch (Exception e) {
            throw e;
        } finally {
            fairLock.unlock();
        }
        return true;
    }
}
