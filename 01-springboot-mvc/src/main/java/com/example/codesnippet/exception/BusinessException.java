package com.example.codesnippet.exception;

import com.example.codesnippet.enums.ErrorConstants;
import lombok.Getter;

/**
 * 自定义业务异常
 */
@Getter
public class BusinessException extends RuntimeException {

    private final String errorCode;

    public BusinessException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorConstants errorConstants) {
        super(errorConstants.getErrorMessage());
        this.errorCode = errorConstants.getErrorCode();
    }
}

