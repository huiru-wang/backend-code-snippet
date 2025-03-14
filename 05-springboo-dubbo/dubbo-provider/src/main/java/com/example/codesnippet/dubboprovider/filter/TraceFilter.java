package com.example.codesnippet.dubboprovider.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;

import java.util.UUID;

@Slf4j
@Activate(group = {CommonConstants.CONSUMER, CommonConstants.PROVIDER})
public class TraceFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String traceId = RpcContext.getServerAttachment().getAttachment("trace-id");
        if (null == traceId || traceId.equals("")) {
            traceId = UUID.randomUUID().toString();
            log.info("TraceFilter set traceId: {}", traceId);
            RpcContext.getServiceContext().setAttachment("trace-id", traceId);
        }
        return invoker.invoke(invocation);
    }
}
