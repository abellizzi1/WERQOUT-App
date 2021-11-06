package com.example.werqoutfrontend.model;

import java.util.ArrayList;

/**
 * @author Angelo Bellizzi
 * Model for a Team object. Contains data for the athletes and workouts.
 * Includes methods needed to manage a team.
 */
public class Team {
    /**
     * ArrayList of the athletes in this Team.
     */
    public ArrayList<Athlete> listAthletes;

    /**
     * ArrayList of the workouts in this Team.
     */
    public ArrayList<Workout> listWorkouts;

    /**
     * The name of the team.
     */
    private String teamName;

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
        listWorkouts = new ArrayList<Workout>();
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
}
