package com.werqout.werqout.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "coaches")
public class Coach {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Id of the Coach",name="id",required=true,value="1")
	private long id;
    
    @OneToOne
    @JoinColumn(name = "managedTeam", referencedColumnName = "id")
    @JsonIgnore
    @ApiModelProperty(notes = "Managed Team of the Athlete",name="managedTeam",required=false,value="team1")
    private Team managedTeam;
    
    @ApiModelProperty(notes = "Username of the Coach",name="userName",required=true,value="testUserName")
    private String userName;

    @ApiModelProperty(notes = "Email of the Coach",name="email",required=true,value="email@example.com")
    private String email;
    
    @ApiModelProperty(notes = "Password of the Coach",name="password",required=true,value="password")
    private String password;

    /**
	 * Value represents average rating of the group, based on user reviews
	 */
    @ApiModelProperty(notes = "Rating of the Coach (out of five stars)",name="rating",required=false,value="5.0")
	private double rating;
    @ApiModelProperty(notes = "Number of ratings the Coach has recieved",name="numRatings",required=false,value="111")
	private int numRatings;
	
	// Constructors ==========================================================================================

    public Coach(){
        this.rating = 0;
        this.numRatings = 0;
    }

    /**
     * Constructor for the coach.
     * @param id ID of the user
     * @param userName user's userName
     * @param email user's email
     * @param password user's password
     * @param groupName name of the group that this user is coaching
     * @param gymName name of the gym at which this user is coaching. 
     * @param rating the average rating this user has received
     * @param numRatings the amount of ratings this user has recieved
     */

    public Coach(long id, String userName, String email, String password, double rating, int numRatings) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.rating = rating;
        this.numRatings = numRatings;
    }
    
    // Basic Getters/Setters =================================================================================
    
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
    
    /**
     * Allows a user to rate a coach and stores that in the database
     * @return rating.
     */
    public double rate(double rating) {
        numRatings++;
        this.rating += rating;
        return this.rating / numRatings;
    }
    
    public int getNumRatings() {
    	return numRatings;
    }

    public double getRating() { 
        return rating;
    }
    
    // Manage managedTeam ====================================================================================
    
    public Team getManagedTeam() {
    	return managedTeam;
    }
    
    public void setManagedTeam(Team team) {
    	managedTeam = team;
    }

    public void removeManagedTeam() {
    	managedTeam = null;
    }
}
