package com.example.werqoutfrontend.model;

import java.util.ArrayList;

/**
 * Model for a Team object. Contains data for the athletes and workouts.
 * Includes methods needed to manage a team.
 * @author Angelo Bellizzi
 */
public class Team {
    /**
     * ArrayList of the athletes in this Team.
     */
    public ArrayList<Athlete> listAthletes;

    /**
     * ArrayList of the workouts in this Team.
     */
    public ArrayList<Event> listWorkouts;

    /**
     * The name of the team.
     */
    private String teamName;

    /**
     * The sum of the ratings for the team.
     */
    private double ratingSum;

    /**
     * The total number of ratings given. (used to compute the average/true rating)
     */
    private int numRatings;

    /**
     * The owner of the gym that this team is at.
     */
    private GymOwner gymOwner;

    /**
     * Constructor for a Team. Creates an ArrayList for the athletes and workouts, and sets
     * the team name to the teamName parameter.
     * @param teamName
     * the name of the team
     */
    public Team(String teamName)
    {
        this.teamName = teamName;
        listAthletes = new ArrayList<Athlete>();
        listWorkouts = new ArrayList<Event>();
        ratingSum = 0;
        numRatings = 0;
    }

    /**
     * Sets the team name to the teamName parameter.
     * @param teamName
     * the new name of the team
     */
    public void setTeamName(String teamName)
    {
        this.teamName = teamName;
    }

    /**
     * Gets the team name.
     * @return
     * returns the name of the team
     */
    public String getTeamName() { return teamName; }

    /**
     * Adds an athlete to the ArrayList and sets the athlete's team to this team.
     * @param a
     * the athlete to be added to the team
     */
    public void addAthlete(Athlete a)
    {
        listAthletes.add(a);
        a.setTeam(this);
    }

    /**
     * Removes an athlete from the ArrayList and sets the athlete's team to null.
     * @param a
     * the athlete to remove from the team
     */
    public void removeAthlete(Athlete a)
    {
        listAthletes.remove(a);
        a.setTeam(null);
    }

    /**
     * Rates the team by adding the rating to the sum and adding 1 to the number of ratings.
     * @param num
     *  The input for the rating.
     */
    public void rate(int num)
    {
        numRatings++;
        ratingSum += num;
    }

    /**
     * Gets the rating for the team.
     * @return
     *  returns the average of all ratings given to the team.
     */
    public double getRating()
    {
        return (ratingSum / numRatings);
    }

    public void setTeamGym(GymOwner go)
    {
        gymOwner = go;
    }

}
