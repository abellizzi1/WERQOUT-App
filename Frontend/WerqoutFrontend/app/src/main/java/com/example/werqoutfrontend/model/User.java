package com.example.werqoutfrontend.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable {
    private String username;
    private String email;
    private String password;
    private String userType;
    private int id;
    public static User currentUser;
    public static boolean loggedIn = false;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getUserType() {
        return userType;
    }

    public int getId() {
        return id;
    }
}
