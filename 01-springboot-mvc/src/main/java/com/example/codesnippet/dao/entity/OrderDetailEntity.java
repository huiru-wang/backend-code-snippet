package com.example.codesnippet.dao.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderDetailEntity {

    private Integer id;

    private String orderId;

    private String skuId;

    private BigDecimal price;

    private BigDecimal actualPrice;

    private Integer quantity;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
