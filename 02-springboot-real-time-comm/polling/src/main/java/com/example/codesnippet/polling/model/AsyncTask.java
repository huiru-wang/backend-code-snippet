package com.example.codesnippet.polling.model;

import lombok.Data;

@Data
public class AsyncTask {

    private String id;

    private String userId;

    private String status;

    public AsyncTask(String id, String userId, String status) {
        this.id = id;
        this.userId = userId;
        this.status = status;
    }
}
