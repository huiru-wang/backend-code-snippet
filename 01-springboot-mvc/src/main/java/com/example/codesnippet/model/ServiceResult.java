package com.example.codesnippet.model;

import com.example.codesnippet.enums.ErrorConstants;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应
 */
@Data
public class ServiceResult<T> implements Serializable {

    private boolean success;

    private T data;

    private String errorCode;

    private String errorMessage;

    private ServiceResult() {}

    public static <T> ServiceResult<T> success(T data) {
        ServiceResult<T> serviceResult = new ServiceResult<>();
        serviceResult.setSuccess(true);
        serviceResult.setData(data);
        return serviceResult;
    }

    public static <T> ServiceResult<T> fail(String errorCode, String errorMsg) {
        ServiceResult<T> serviceResult = new ServiceResult<>();
        serviceResult.setSuccess(false);
        serviceResult.setErrorCode(errorCode);
        serviceResult.setErrorMessage(errorMsg);
        return serviceResult;
    }

    public static <T> ServiceResult<T> fail(ErrorConstants errorConstants){
        ServiceResult<T> serviceResult = new ServiceResult<>();
        serviceResult.setSuccess(false);
        serviceResult.setErrorCode(errorConstants.getErrorCode());
        serviceResult.setErrorMessage(errorConstants.getErrorMessage());
        return serviceResult;
    }
}