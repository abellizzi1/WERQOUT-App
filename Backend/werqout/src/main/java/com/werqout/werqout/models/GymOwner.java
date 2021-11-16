package com.werqout.werqout.models;


import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@Entity
public class GymOwner{
     /**
     * User ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Id of the Athlete",name="id",required=true,value="1")
    private long id;
    /**
     * username of the gym owner
     */
    @ApiModelProperty(notes = "Username of the Athlete",name="userName",required=true,value="testUserName")
    private String userName;
    /**
     * email of the gym owner- will be checked with REGEX
     */
    @ApiModelProperty(notes = "Email of the Athlete",name="email",required=true,value="email@example.com")
    private String email;
    /**
     * Password of the gym owner- hashed in DB and will be checked with REGEX
     */
    private String password;
    /**
     * Name of the gym that the owner is an owner of
     */
    @ApiModelProperty(notes = "Password of the Athlete",name="password",required=true,value="testpassword")
    private String gymName;

    //todo have an event that a gym owner can create and then make joinable by coaches and their teams
    /**
     * Groups that go to the gym. Foreign key to groups table. 
     */
    @OneToMany
    @JsonIgnore
    @JoinTable(name="gym_teams",
                joinColumns = @JoinColumn(name = "gym_owner_id"), 
                inverseJoinColumns = @JoinColumn(name="team_id"))
                @ApiModelProperty(notes = "Teams that are train at this gym",name="teams",required=false,value="Team1, Team2")            
    private List<Team> teams = new ArrayList<Team>();

    /**
     * Event that the gymOwner hosts
     */
    @OneToOne
    @JoinColumn(name = "event_id")
    @ApiModelProperty(notes = "Event that is taking place at this gym",name="event",required=false,value="Event 1")
    private Event event;    

    /**
	 * Value represents average rating of the group, based on user reviews
	 */
    @ApiModelProperty(notes = "Rating of the Gym (out of five stars)",name="rating",required=false,value="5.0")
	private double rating;
    /**
     * number of ratings gymOwner has recieved (will show up as gym)
     */
    @ApiModelProperty(notes = "Number of ratings the Coach has recieved",name="numRatings",required=false,value="111")
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
        return rating / numRatings;
    }
    /**
     * Allows a user to rate a coach and stores that in the database
     */
    public void rate(double rating) {
        numRatings++;
        this.rating += rating;   
    }
    public int getNumRatings() {
    	return numRatings;
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
