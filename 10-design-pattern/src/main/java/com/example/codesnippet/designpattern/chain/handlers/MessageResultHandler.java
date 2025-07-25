package com.example.codesnippet.designpattern.chain.handlers;

import com.example.codesnippet.designpattern.chain.Message;
import com.example.codesnippet.designpattern.chain.MessageHandler;

/**
 * create by whr on 2023-07-05
 */
public class MessageResultHandler implements MessageHandler {

    @Override
    public int order() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void handle(Message message) {
        message.setStatus("Done");
        System.out.println("Message Process Done");
    }
}
