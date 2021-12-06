package com.example.werqoutfrontend.model;

import java.util.ArrayList;

public class GymOwner extends User{

    /**
     * The list of teams at the gym.
     */
    public ArrayList<Team> listTeams;

    /**
     * The current event at the gym.
     */
    private Event gymEvent;

    /**
     * Constructs a new user with the given parameters
     *
     * @param username The username of the user
     * @param email    The email of the user
     * @param password The password of the user
     * @param userType The type of user
     * @param id       The id of the user
     */
    public GymOwner(String username, String email, String password, String userType, int id) {
        super(username, email, password, userType, id);
        listTeams = new ArrayList<Team>();
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
    public void deleteGymEvent()
    {
        gymEvent = null;
    }
}
