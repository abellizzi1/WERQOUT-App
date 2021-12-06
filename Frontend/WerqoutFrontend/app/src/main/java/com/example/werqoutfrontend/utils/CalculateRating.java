package com.example.werqoutfrontend.utils;

import com.example.werqoutfrontend.model.Coach;

public class CalculateRating {
    public static double calculate(double current, int rating, int numR)
    {
        double newRating = (current + rating) /  numR;
        return newRating;
    }
}
