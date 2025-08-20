package com.example.codesnippet.exception;

import com.example.codesnippet.enums.ErrorConstants;
import lombok.Getter;

/**
 * 自定义业务异常
 */
@Getter
public class BusinessException extends RuntimeException {

    private final String errorCode;

    private final String errorMessage;

    public BusinessException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BusinessException(ErrorConstants errorConstants) {
        super(errorConstants.getErrorMessage());
        this.errorCode = errorConstants.getErrorCode();
        this.errorMessage = errorConstants.getErrorMessage();
    }
}

