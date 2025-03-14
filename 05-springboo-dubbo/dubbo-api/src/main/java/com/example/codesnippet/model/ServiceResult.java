package com.example.codesnippet.model;

import java.io.Serializable;

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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
