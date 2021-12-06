package com.example.werqoutfrontend.model;

import java.io.Serializable;

public class GymOwner extends User implements Serializable {
    private double rating;
    private int numRatings;
    public GymOwner(String email, String password, String username, int id, double rating, int numRatings)
    {
        super(username, email, password, "GymOwner", id);
        this.rating = rating;
        this.numRatings = numRatings;
    }
    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }
}
