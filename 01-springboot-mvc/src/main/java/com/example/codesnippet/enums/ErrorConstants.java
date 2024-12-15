package com.example.codesnippet.enums;

import lombok.Getter;

/**
 * 错误码：模块 | 编号 | 类型
 */
@Getter
public enum ErrorConstants {

    SUCCESS("0", "success"),

    INTERNAL_SERVER_ERROR("1", "Internal Server Error"),

    // *************************** 10 ***************************

    PARAM_INVALID("100001", "param invalid"),

    AUTHENTICATION_FAIL("100002", "authentication fail"),

    // *************************** 11 ***************************
    USER_INFO_UPDATE_FAIL("110001", "user info update fail"),

    USER_CREATE_FAIL("110002", "user create fail"),
    ;

    private final String errorCode;

    private final String errorMessage;

    ErrorConstants(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
