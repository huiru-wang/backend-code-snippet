package com.example.codesnippet.dubboprovider.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.BaseFilter;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;

import java.util.Arrays;

/**
 * PROVIDER、CONSUMER都有效
 */
@Slf4j
@Activate(group = {CommonConstants.CONSUMER, CommonConstants.PROVIDER})
public class LogFilter implements Filter, BaseFilter.Listener {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        String serviceName = invocation.getServiceName();
        String methodName = invocation.getMethodName();
        Object[] arguments = invocation.getArguments();
        log.info("LogFilter: [{}] [{}] [{}]", serviceName, methodName, Arrays.toString(arguments));
        return invoker.invoke(invocation);
    }

    @Override
    public void onResponse(Result appResponse, Invoker<?> invoker, Invocation invocation) {
        String serviceName = invocation.getServiceName();
        String methodName = invocation.getMethodName();
        Object[] arguments = invocation.getArguments();
        log.info("LogFilter onResponse: [{}] [{}] [{}]", serviceName, methodName, Arrays.toString(arguments));
    }

    @Override
    public void onError(Throwable t, Invoker<?> invoker, Invocation invocation) {
        log.error("LogFilter onError: [{}]", t.getMessage());
    }
}
