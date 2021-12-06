package com.example.werqoutfrontend.utils;

import com.example.werqoutfrontend.model.Coach;

public class CalculateRating {
    public CalculateRating(){
        
    }
    public double calc(double current, int rating, int numR)
    {
        double newRating = (current + rating) /  numR;
        return newRating;
    }
}
