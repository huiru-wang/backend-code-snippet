package com.example.codesnippet.service;

import com.example.codesnippet.model.DemoMessage;
import com.example.codesnippet.model.ServiceResult;

public interface DemoService {

    ServiceResult<DemoMessage> echo(String content);
}
