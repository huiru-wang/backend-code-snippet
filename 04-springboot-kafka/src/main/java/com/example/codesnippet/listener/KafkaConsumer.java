package com.example.codesnippet.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "code-snippet-topic", groupId = "code-snippet-group")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }

}
