package com.werqout.werqout.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "coaches")
public class Coach {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    
    @OneToOne
    @JoinColumn(name = "managedTeam", referencedColumnName = "id")
    @JsonIgnore
    private Team managedTeam;
    
    private String userName;
    
    private String email;
    
    private String password;

    /**
	 * Value represents average rating of the group, based on user reviews
	 */
	private double rating;
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
        this.rating += rating;
        return this.rating / ++numRatings;
    }
    
    public int getNumRatings() {
    	return numRatings;
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
