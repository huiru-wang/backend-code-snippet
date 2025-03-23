package com.example.codesnippet.polling.controller;

import com.example.codesnippet.polling.model.AsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController("/")
public class PollingController {

    private static final String FINISHED = "FINISHED";

    private static final String INIT = "INIT";

    private final Map<String, AsyncTask> taskMap = new ConcurrentHashMap<>();

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    @PostMapping("/submitTask")
    @CrossOrigin("*")
    public ResponseEntity<String> submitTask(@RequestBody String userId) {
        // 创建任务
        String taskId = UUID.randomUUID().toString();
        AsyncTask asyncTask = new AsyncTask(taskId, userId, INIT);
        taskMap.put(taskId, asyncTask);

        // 执行任务: 10s后任务完成，设置为FINISHED状态
        executor.schedule(() -> executeTask(taskId), 10, TimeUnit.SECONDS);

        // 先返回任务id
        return ResponseEntity.ok(taskId);
    }

    /**
     * 根据TaskId 执行短轮询，立即返回
     */
    @GetMapping("/polling")
    @CrossOrigin("*")
    public ResponseEntity<String> polling(@RequestParam String taskId) {
        AsyncTask asyncTask = taskMap.get(taskId);
        if (null == asyncTask) {
            return ResponseEntity.ok("TASK NOT EXISTS");
        }
        String status = asyncTask.getStatus();
        if (Objects.equals(status, FINISHED)) {
            return ResponseEntity.ok("DONE");
        }
        return ResponseEntity.ok("NOT READY");
    }

    /**
     * 根据TaskId 执行长轮询，hold一定时间再返回
     */
    @GetMapping("/longPolling")
    @CrossOrigin("*")
    public ResponseEntity<String> longPolling(@RequestParam String taskId) {
        AsyncTask asyncTask = taskMap.get(taskId);
        if (null == asyncTask) {
            return ResponseEntity.ok("TASK NOT EXISTS");
        }
        long startTime = System.currentTimeMillis();
        // 轮询30s 频率1s
        while (System.currentTimeMillis() - startTime < 30000) {
            asyncTask = taskMap.get(taskId);
            String status = asyncTask.getStatus();
            if (Objects.equals(status, FINISHED)) {
                return ResponseEntity.ok("DONE");
            }
            try {
                // 暂停 1 秒，避免 CPU 占用过高
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return ResponseEntity.ok("NOT READY");
    }

    // 模拟任务结束
    private void executeTask(String taskId) {
        AsyncTask asyncTask = taskMap.get(taskId);
        asyncTask.setStatus(FINISHED);
    }
}
