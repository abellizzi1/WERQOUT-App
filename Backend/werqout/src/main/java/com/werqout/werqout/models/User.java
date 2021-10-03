package com.werqout.werqout.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class User {
    /**
     * Users ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY);
    private long id;
    /**
     * Username of user
     */
    private String userName;
    /**
     * Users email
     */
    private String email;
    /**
     * Users password
     */
    private String password;
    /**
     * Default contstructor
     */
    public User(){

    }
    /**
     * Constructor of a User object
     * @param userName user's userName
     * @param email user's email
     * @param password  user's password
     */
    public User(long id, String userName, String email, String password) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
    
    
}
