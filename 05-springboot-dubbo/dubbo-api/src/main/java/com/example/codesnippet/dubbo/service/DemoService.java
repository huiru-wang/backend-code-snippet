package com.example.codesnippet.dubbo.service;

import com.example.codesnippet.dubbo.model.DemoMessage;
import com.example.codesnippet.dubbo.model.ServiceResult;

public interface DemoService {

    ServiceResult<DemoMessage> echo(String content);
}
