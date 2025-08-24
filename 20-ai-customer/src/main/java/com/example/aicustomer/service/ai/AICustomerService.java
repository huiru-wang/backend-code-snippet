package com.example.aicustomer.service.ai;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.community.model.dashscope.QwenStreamingChatModel;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class AICustomerService {

    private final QwenChatModel qwenChatModel;

    private final QwenStreamingChatModel qwenStreamingChatModel;

    public AICustomerService(QwenChatModel qwenChatModel,
                             QwenStreamingChatModel qwenStreamingChatModel) {
        this.qwenChatModel = qwenChatModel;
        this.qwenStreamingChatModel = qwenStreamingChatModel;
    }

    public String chat(String message) {
        UserMessage userMessage = UserMessage.from(message);
        ChatResponse chatResponse = qwenChatModel.chat(userMessage);
        return chatResponse.aiMessage().text();
    }

    public StreamingResponseBody streamingChat(String message) {
        return outputStream -> {
            qwenStreamingChatModel.chat(message, new StreamingChatResponseHandler() {
                @Override
                public void onPartialResponse(String token) {
                    log.info("Streaming Partial Token: {}", token);
                    try {
                        outputStream.write(token.getBytes(StandardCharsets.UTF_8));
                    } catch (IOException e) {
                        // ignore
                    }
                }

                @Override
                public void onCompleteResponse(ChatResponse chatResponse) {
                    try {
                        // 可选：发送结束标记
                        outputStream.write("\n[END]\n".getBytes(StandardCharsets.UTF_8));
                        outputStream.flush();
                    } catch (IOException e) {
                        // ignore
                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    try {
                        // 发送错误信息
                        outputStream.write(("[ERROR] " + throwable.getMessage() + "\n").getBytes(StandardCharsets.UTF_8));
                        outputStream.flush();
                    } catch (IOException e) {
                        // ignore
                    }
                }
            });
        };
    }
}
