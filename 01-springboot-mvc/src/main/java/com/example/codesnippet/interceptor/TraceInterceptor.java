package com.example.codesnippet.interceptor;

import com.example.codesnippet.constants.GlobalConstants;
import com.example.codesnippet.util.IdUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 增加服务间的 trace id 追踪全链路日志
 */
@Slf4j
public class TraceInterceptor implements HandlerInterceptor {

    /**
     * 没有Trace，则添加
     */
    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        log.info("[Interceptor]: preHandler");
        String traceId = request.getHeader(GlobalConstants.X_TRACE_ID);
        if (StringUtils.isEmpty(traceId)) {
            MDC.put(GlobalConstants.TRACE_ID, IdUtils.UUID());
        } else {
            MDC.put(GlobalConstants.TRACE_ID, traceId);
        }
        return true;
    }

    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, ModelAndView modelAndView) throws Exception {
        String traceId = MDC.get(GlobalConstants.X_TRACE_ID);
        log.info("[Interceptor]: postHandler, TraceId: {}", traceId);
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("[Interceptor]: afterCompletion");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
