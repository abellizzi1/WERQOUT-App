package com.werqout.werqout.models;

import java.util.ArrayList;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.ManyToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
// @Table(name = "gym_owner")
public class GymOwner extends User {
    /**
     * Name of the gym that the owner is an owner of
     */
    private String gymName;

    //todo have an event that a gym owner can create and then make joinable by coaches and their teams
    /**
     * Groups that go to the gym. Foreign key to groups table. 
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "teams_in_gym",
               joinColumns = @JoinColumn(name= "gym_owner_id"),
               inverseJoinColumns = @JoinColumn(name="group_id"))
    @JsonIgnore
            //delcare as a list because hibernate doesn't support arraylist
    private List<Team> teams = new ArrayList<Team>();

    /**
	 * Value represents average rating of the group, based on user reviews
	 */
	private double rating;
    /**
     * number of ratings gymOwner has recieved (will show up as gym)
     */
	private int numRatings;
    /**
     * Default constructor
     */
    public GymOwner(){
        this.rating = 0;
        this.numRatings = 0;
    }
    /**
     * Constructor for a GymOwner
     * @param id
     * @param userName
     * @param email
     * @param password
     * @param gymName
     * @param groups
     */
    public GymOwner(long id, String userName, String email, String password, String gymName) {
        super(id, userName, email, password);
        this.gymName = gymName;
        this.rating = 0;
        this.numRatings = 0;
    }
    public String getGymName() {
        return gymName;
    }
    public void setGymName(String gymName) {
        this.gymName = gymName;
    }
    public List<Team> getTeams() {
        return teams;
    }
    public void addTeam(Team team) {
        teams.add(team);
    }
    public void removeTeam(Team team) {
        teams.remove(team);
    }
    
    public double getRating() {
        return rating;
    }
    /**
     * Allows a user to rate a coach and stores that in the database
     * @return rating.
     */
    public double rate(){
        numRatings++;
        return rating /= numRatings;
    }

    





}
