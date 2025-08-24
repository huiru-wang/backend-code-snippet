package com.example.aicustomer.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerChatResponse {

    private String content;
}
