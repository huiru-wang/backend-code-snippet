package com.example.codesnippet.webapp.exception;

import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DegradeExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DegradeException.class)
    public JSONObject handleBusinessException(DegradeException e) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "400");
        jsonObject.put("message", "degraded");
        return jsonObject;
    }
}
