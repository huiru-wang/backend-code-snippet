package com.example.codesnippet.sse.model;

import lombok.Data;

@Data
public class ChatRequest {

    private String userId;

    private String content;
}
