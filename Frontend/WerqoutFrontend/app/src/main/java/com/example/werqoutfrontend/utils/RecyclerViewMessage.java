package com.example.werqoutfrontend.utils;

public class RecyclerViewMessage {
    private String message;
    private String username;
    private boolean sender;

    public RecyclerViewMessage (String message, String username)
    {
        this.message = message;
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSender() {
        return sender;
    }

    public void setSender(boolean sender) {
        this.sender = sender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
