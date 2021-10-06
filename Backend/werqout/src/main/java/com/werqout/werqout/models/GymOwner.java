package com.werqout.werqout.models;

import java.util.ArrayList;

import javax.persistence.Entity;

@Entity
public class GymOwner extends User {
    /**
     * Name of the gym that the owner is an owner of
     */
    private String gymName;

    /**
     * Groups that go to the gym. Foreign key to groups table. 
     */
    private ArrayList<Integer> groups;

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
    public GymOwner(long id, String userName, String email, String password, String gymName, ArrayList<Integer> groups) {
        super(id, userName, email, password);
        this.gymName = gymName;
        this.groups = groups;
        this.rating = 0;
        this.numRatings = 0;
    }
    public String getGymName() {
        return gymName;
    }
    public void setGymName(String gymName) {
        this.gymName = gymName;
    }
    public ArrayList<Integer> getGroups() {
        return groups;
    }
    public void setGroups(ArrayList<Integer> groups) {
        this.groups = groups;
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
