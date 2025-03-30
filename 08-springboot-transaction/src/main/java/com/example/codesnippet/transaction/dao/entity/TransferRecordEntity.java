package com.example.codesnippet.transaction.dao.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransferRecordEntity {

    private String id;

    private String recordId;

    private String fromUserId;

    private String toUserId;

    private Double amount;

    private String status;

    private LocalDateTime transferAt;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
