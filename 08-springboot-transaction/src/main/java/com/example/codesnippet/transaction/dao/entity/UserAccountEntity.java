package com.example.codesnippet.transaction.dao.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserAccountEntity {

    private String id;

    private String userId;

    private Double balance;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
