package com.example.codesnippet.designpattern.chain.handlers;


import com.example.codesnippet.designpattern.chain.Message;
import com.example.codesnippet.designpattern.chain.MessageHandler;

/**
 * create by whr on 2023-07-05
 */
public class MessageConvertHandler implements MessageHandler {
    @Override
    public int order() {
        return 1;
    }

    @Override
    public void handle(Message message) {
        message.setStatus("Processing");
        message.setDetails("Converted");
        System.out.println("Message Is Processing");
    }
}
