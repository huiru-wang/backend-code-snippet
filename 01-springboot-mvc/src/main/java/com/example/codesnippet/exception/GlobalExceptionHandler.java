package com.example.codesnippet.exception;

import com.example.codesnippet.enums.ErrorConstants;
import com.example.codesnippet.model.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 异常流顺序：
 * 方法抛出ex --> aop@AfterThrowing可处理异常 --> @RestControllerAdvice --> interceptor.afterCompletion()方法处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常，指定捕获自定义的业务异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ServiceResult<Void> handleBusinessException(BusinessException e) {
        return ServiceResult.fail(e.getErrorCode(), e.getMessage());
    }

    /**
     * 处理参数异常，指定捕获的相关的异常类型
     *
     * @param e IllegalArgumentException
     * @return BaseResponse
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class, MissingServletRequestParameterException.class, MethodArgumentNotValidException.class})
    public ServiceResult<Void> handleIllegalArgument(Exception e) {
        log.error("IllegalArgument: ", e);
        StringBuilder errorMessage = new StringBuilder(ErrorConstants.PARAM_INVALID.getErrorMessage());
        errorMessage.append(": ");
        if (e instanceof MissingServletRequestParameterException ex) {

            // lombok 校验异常
            String parameterName = ex.getParameterName();
            errorMessage.append(parameterName);

        } else if (e instanceof IllegalArgumentException ex) {

            // Assert 校验异常
            String message = ex.getMessage();
            errorMessage.append(message);

        } else if (e instanceof MethodArgumentNotValidException ex) {

            // @Validate注解异常
            BindingResult bindingResult = ex.getBindingResult();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            allErrors.forEach(error -> {
                FieldError fieldError = (FieldError) error;
                String field = fieldError.getField();
                String message = fieldError.getDefaultMessage();
                errorMessage.append("[").append(field).append("]: ").append(message);
            });
        }
        return ServiceResult.fail(ErrorConstants.PARAM_INVALID.getErrorCode(), errorMessage.toString());
    }

    /**
     * 兜底其他未知异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Throwable.class)
    public ServiceResult<Void> handleException(Throwable e) {
        log.error("Internal Server Error: ", e);
        return ServiceResult.fail(ErrorConstants.INTERNAL_SERVER_ERROR);
    }
}
