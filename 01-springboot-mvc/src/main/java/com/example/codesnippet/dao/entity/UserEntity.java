package com.example.codesnippet.dao.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserEntity {

    private Integer id;

    private String userId;

    private String username;

    private String password;

    private String phoneNo;

    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
