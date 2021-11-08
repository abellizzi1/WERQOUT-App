package com.example.werqoutfrontend.model;

import java.io.Serializable;

/**
 * A class that encapsulates all of the users of the app.
 */

@SuppressWarnings("serial")
public class User implements Serializable {
    /**
     * The username of the user
     */
    private String username;
    /**
     * The email of the user
     */
    private String email;
    /**
     * The password of the user
     */
    private String password;
    /**
     * The type of user (Athlete, Coach, Gym Owner)
     */
    private String userType;
    /**
     * The unique identification number for each user
     */
    private int id;
    /**
     * A static instance of user that represents the current client of the application
     */
    public static User currentUser;
    /**
     * A boolean value that represents if the user is logged in or not
     */
    public static boolean loggedIn = false;

    /**
     * Constructs a new user with the given parameters
     * @param username
     *  The username of the user
     * @param email
     *  The email of the user
     * @param password
     *  The password of the user
     * @param userType
     *  The type of user
     * @param id
     *  The id of the user
     */
    public User(String username, String email, String password, String userType, int id)
    {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.id = id;

        if(loggedIn == false)
        {
            loggedIn = true;
            currentUser = this;
        }
    }
    /**
     * Gets the username of the user
     * @return
     *  The username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Updates the username of the user
     * @param username
     *  The new username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Returns the email of the user
     * @return
     *  The email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Updates the email of the user
     * @param email
     *  The new email for the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password of the user
     * @return
     *  The password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Updates the password of the user
     * @param password
     *  The new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Shows whether or not the user is logged in
     * @return
     *  True if the user is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Updates the current login status of a user
     * @param loggedIn
     *  The new login status of the user
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * Returns the current type of user
     * @return
     *  The type of user
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Gets the id of the user
     * @return
     *  The id of the user
     */
    public int getId() {
        return id;
    }
}
