package com.example.codesnippet.webhook.model;

import lombok.Data;

@Data
public class ThirdServiceWebhookRequest {

    private String businessId;

    private String businessData;
}
