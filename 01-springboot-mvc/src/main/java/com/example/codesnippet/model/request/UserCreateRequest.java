package com.example.codesnippet.model.request;

import lombok.Data;

@Data
public class UserCreateRequest {

    private String username;

    private String password;

    private String phoneNo;

    private String email;
}
