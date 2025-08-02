package com.example.codesnippet.rocketmq.exception;

public class BusinessException extends RuntimeException{

    public BusinessException(String errorMessage) {
        super(errorMessage);
    }
}
