package com.example.werqoutfrontend.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Athlete extends User implements Serializable {

    public Athlete(String email, String password, String username, int id)
    {
        super(username, email, password, "Athlete", id);
    }

}
