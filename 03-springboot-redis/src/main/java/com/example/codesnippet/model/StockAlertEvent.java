package com.example.codesnippet.model;

import lombok.Data;

@Data
public class StockAlertEvent {

    private String messageId;

    private String stockId;

    private String price;
}
