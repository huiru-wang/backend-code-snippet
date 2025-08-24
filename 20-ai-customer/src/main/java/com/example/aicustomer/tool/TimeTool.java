package com.example.aicustomer.tool;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TimeTool {

    /**
     * 获取当前时间的字符串表示（格式：yyyy-MM-dd HH:mm:ss）
     */
    @Tool(name = "getLocalDateTime")
    public String getLocalDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
