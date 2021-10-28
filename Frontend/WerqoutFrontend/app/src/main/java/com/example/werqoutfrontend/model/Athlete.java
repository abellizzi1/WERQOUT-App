package com.example.werqoutfrontend.model;

public class Athlete extends User{

    public Athlete(String email, String password, String username, int id)
    {
        super(username, email, password, "Athlete", id);
    }

}
