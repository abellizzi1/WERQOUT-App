package com.werqout.werqout.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Coach extends User {
    /**
     * Name of the group this type of user coaches. Will be a foregin key to the groups table.
     */
    private String groupName;
    /**
     * Name of the primary gym this type of User coaches at
     */
    private String gymName;
    
    @OneToOne
    @JoinColumn(name = "coachId", referencedColumnName = "id")
    private Team managedTeam;

    /**
	 * Value represents average rating of the group, based on user reviews
	 */
	private double rating;
	private int numRatings;

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
    public Coach(long id, String userName, String email, String password, String groupName, String gymName,
            double rating, int numRatings) {
        super(id, userName, email, password);
        this.groupName = groupName;
        this.gymName = gymName;
        this.rating = rating;
        this.numRatings = numRatings;
    }
    /**
     * Allows a user to rate a coach and stores that in the database
     * @return rating.
     */
    public double rate() {
        numRatings++;
        return rating /= numRatings;
    }

}
