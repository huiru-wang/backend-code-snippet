package com.example.codesnippet.designpattern.chain.handlers;

import com.example.codesnippet.designpattern.chain.Message;
import com.example.codesnippet.designpattern.chain.MessageHandler;

/**
 * create by whr on 2023-07-05
 */
public class MessagePreProcessHandler implements MessageHandler {


    @Override
    public int order() {
        return -1;
    }

    @Override
    public void handle(Message message) {
        message.setStatus("Init");
        System.out.println("Message Init Done");
    }
}
