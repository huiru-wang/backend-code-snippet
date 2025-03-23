package com.example.codesnippet.polling.service;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class TaskService {

    // 任务队列执行器
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public void submitTask() {

    }
}
