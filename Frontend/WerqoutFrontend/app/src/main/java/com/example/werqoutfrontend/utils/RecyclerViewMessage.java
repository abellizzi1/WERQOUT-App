package com.example.werqoutfrontend.utils;

/**
 * A class that represents the message components that will be displayed within the messages recycler
 * view.
 * @author Colin
 */
public class RecyclerViewMessage {
    /**
     * The message
     */
    private String message;
    /**
     * The username of who sent the message
     */
    private String username;

    /**
     *  Creates a new component to be displayed within the messages recycler view
     * @param message
     *  The message being sent
     * @param username
     *  The username of who sent the message
     */
    public RecyclerViewMessage (String message, String username)
    {
        this.message = message;
        this.username = username;
    }

    /**
     * Returns the message that was sent
     * @return
     *  The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the username of who sent the message
     * @return
     *  The username of the sender
     */
    public String getUsername() {
        return username;
    }
}
