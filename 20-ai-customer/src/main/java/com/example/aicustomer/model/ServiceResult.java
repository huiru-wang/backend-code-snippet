package com.example.aicustomer.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServiceResult<T> implements Serializable {

    private boolean success;

    private T data;

    private String code;

    private String message;

    public static <T> ServiceResult<T> success(T data) {
        ServiceResult<T> result = new ServiceResult<>();
        result.setData(data);
        result.setSuccess(true);
        result.setCode("0");
        return result;
    }

    public static <T> ServiceResult<T> fail(String code, String message) {
        ServiceResult<T> result = new ServiceResult<>();
        result.setCode(code);
        result.setMessage(message);
        result.setSuccess(false);
        return result;
    }
}
