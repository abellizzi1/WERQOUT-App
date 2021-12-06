package com.example.werqoutfrontend.model;

import java.util.ArrayList;
import java.io.Serializable;

public class GymOwner extends User implements Serializable {
    /**
     * The list of teams at the gym.
     */
    public ArrayList<Team> listTeams;
    private Event gymEvent;
    private double rating;
    private int numRatings;
    public GymOwner(String email, String password, String username, int id, double rating, int numRatings)
    {
        super(username, email, password, "GymOwner", id);
        this.rating = rating;
        this.numRatings = numRatings;
        listTeams = new ArrayList<Team>();
    }
    public double getRating() {
        return rating/numRatings;
    }

    public void setRating(double rating) {
        this.rating += rating;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }

    /**
     * Sets the gym event given the parameter.
     * @param e
     *  The gym's new event.
     */
    public void setGymEvent(Event e)
    {
        gymEvent = e;
    }

    /**
     * Deletes the current gym event and gymEvent is set to null.
     */
    public void deleteGymEvent() {
        gymEvent = null;
    }

    /**
     * Adds a team to the gym owner's list of teams.
     * @param t
     *  The team to be added. This team's gym owner is set to this gym owner.
     */
    public void addTeam (Team t)
    {
        listTeams.add(t);
        t.setTeamGym(this);
    }

    /**
     * Removes a team from the gym owner's list of teams.
     * @param t
     *  The team to be removed. This team's gym owner is set to null.
     */
    public void removeTeam (Team t)
    {
        listTeams.remove(t);
        t.setTeamGym(null);
    }
}
