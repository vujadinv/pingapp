package com.company.pingapp.client.messages;

public class Message {
    private final int id;
    private final String message;


    public Message(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
