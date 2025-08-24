package com.example.aicustomer.config;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.community.model.dashscope.QwenLanguageModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * langchain4j-community-dashscope中提供了4个可用的模型：
 * <pre>
 *  1. QwenChatModel
 *  2. QwenStreamingChatModel
 *  3. QwenLanguageModel
 *  4. QwenStreamingLanguageModel
 * </pre>
 *
 * 这里注释，已在<application.properties>中定义
 */
@Configuration
public class AIModelConfig {

    private final String API_KEY = "";

//    @Bean
//    public QwenChatModel qwenChatModel() {
//        return QwenChatModel.builder().apiKey(API_KEY).modelName("qwen-plus").build();
//    }
//
//    @Bean
//    public QwenLanguageModel qwenLanguageModel() {
//        return QwenLanguageModel.builder()
//                .apiKey(API_KEY)
//                .modelName("qwen-plus")
//                .enableSearch(true)
//                .temperature(0.7f)
//                .maxTokens(4096)
//                .build();
//    }
}
