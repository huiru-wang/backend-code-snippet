package com.example.codesnippet.designpattern.chain;

public interface MessageHandler {

    int order();

    void handle(Message message);
}
