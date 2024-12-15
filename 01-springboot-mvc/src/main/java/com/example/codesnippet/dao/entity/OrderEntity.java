package com.example.codesnippet.dao.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderEntity {

    private Integer id;

    private String orderId;

    private String userId;

    private Integer type;

    private Integer status;

    private String paymentType;

    private BigDecimal amount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
