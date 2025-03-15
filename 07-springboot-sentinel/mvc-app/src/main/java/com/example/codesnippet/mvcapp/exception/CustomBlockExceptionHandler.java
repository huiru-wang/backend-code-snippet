package com.example.codesnippet.mvcapp.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.example.codesnippet.mvcapp.model.ServiceResult;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class CustomBlockExceptionHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        // 设置响应头
        response.setContentType("application/json;charset=UTF-8");
        ServiceResult<Object> fail = ServiceResult.fail("429", "请求被限流，请稍后再试");
        try {
            response.getWriter().write(JSON.toJSONString(fail));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
