package com.werqout.werqout.models;

import javax.persistence.CascadeType;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
public class GymOwner{
     /**
     * User ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * username of the gym owner
     */
    private String userName;
    /**
     * email of the gym owner- will be checked with REGEX
     */
    private String email;
    /**
     * Password of the gym owner- hashed in DB and will be checked with REGEX
     */
    private String password;
    /**
     * Name of the gym that the owner is an owner of
     */
    private String gymName;

    //todo have an event that a gym owner can create and then make joinable by coaches and their teams
    /**
     * Groups that go to the gym. Foreign key to groups table. 
     */
    @OneToMany
    @JoinTable(name="gym_teams",
                joinColumns = @JoinColumn(name = "gym_owner_id"), 
                inverseJoinColumns = @JoinColumn(name="team_id"))
    private List<Team> teams = new ArrayList<Team>();

    /**
     * Event that the gymOwner hosts
     */
    @OneToOne
    @JoinColumn(name = "event_id")
    private Event event;    

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
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password; 
        this.gymName = gymName;
        this.rating = 0;
        this.numRatings = 0;
    }
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getGymName() {
        return gymName;
    }
    public void setGymName(String gymName) {
        this.gymName = gymName;
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

    //team methods
    @JsonIgnore
    public List<Team> getTeams() {
        return teams;
    }
    public void addTeam(Team team) {
        teams.add(team);
    }
    public void removeTeam(Team team) {
        teams.remove(team);
    }
    public boolean isInTeam(Team team){
        return teams.contains(team);
    }
    
    //event methods
    @JsonIgnore
    public Event getEvent(){
        return event;
    }

    public void setEvent(Event event){
        this.event = event;
    }

    





}
