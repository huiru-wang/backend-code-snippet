package com.example.codesnippet.transaction.model;

import lombok.Data;

@Data
public class TransferRequest {

    private String fromUserId;

    private String toUserId;

    private Double amount;

    // 本次事务是否成功
    private boolean rollback;
}
