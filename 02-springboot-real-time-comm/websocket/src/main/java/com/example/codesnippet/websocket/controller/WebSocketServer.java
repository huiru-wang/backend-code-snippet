package com.example.codesnippet.websocket.controller;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * ws://localhost:8080/ws/{userId}
 */
@Slf4j
@ServerEndpoint(value = "/ws/{userId}")
@Component
public class WebSocketServer {

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        log.info("[{}] Connected", userId);
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        log.info("Server Receive Message: [{}]", message);
        String serverMessage = "Server Echo: [" + message + "]";
        session.getBasicRemote().sendText(serverMessage);
    }

    @OnError
    public void OnError(Session session, Throwable e) {
        e.printStackTrace();
    }

    @OnClose
    public void OnClose(Session session) {
        log.info("Session Closed");
    }
}