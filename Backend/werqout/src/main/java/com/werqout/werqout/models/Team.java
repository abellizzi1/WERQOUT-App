package com.werqout.werqout.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.OneToOne;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
public class Team {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	/**
	 * Name of the team
	 */
	private String name;
	
	/**
	 * Array which holds members of the team
	 * Foreign key for Athlete table
	 */
	@ManyToMany(mappedBy = "teams")
	@JsonIgnore
	private List<Athlete> members = new ArrayList<Athlete>();
	
	@JsonIgnore
	@OneToOne(mappedBy = "managedTeam")
	private Coach coach;
	
	/**
	 * Object which represents the team's coach
	 * Foreign key to Athlete table
	 */
	// TODO: add coach dependency once coach class is working
	//@ManyToOne
	//private int coach;
	
	/**
	 * String providing a general description of the team
	 */
	private String description;
	
	// TODO: Create gyms and add usual gym as an instance var
	
	/**
	 * Value represents average rating of the team, based on user reviews
	 */
	private double rating;
	private Integer numRatings;

	@ManyToOne
    @JoinColumn(name = "gym_owwner_id")
    @JsonIgnore
	private GymOwner gymOwner;
	
	/**
	 * Create a new teamand set key parameters
	 * 
	 * @param name
	 * 		String name			: Name of teamto be created
	 * 
	 * @param members
	 * 		int[] members		: Array of ints which are keys to the athlete table, to be added to members list
	 * 
	 * @param coach
	 * 		int coach			: Int which is a key to the athlete table, sets this as team's coach
	 * 
	 * @param description
	 * 		String description:	: String to be team's general description
	 */

	public Team(long id, String name, String description) {
		this.id = id;
		this.name = name;
		
		// Iterate through members argument, add each member to members ArrayList
		
		this.description = description;
		
		// Set rating to 0, numRatings to 0 by default
		rating = 0;
		numRatings = 0;
	}
	
	/**
	 * Default constructor for team, only variable defined is rating and numRatings
	 */

	public Team() {
		this.rating = 0;
		this.numRatings = 0;
	}
	
	/*
	 * General getters and setters, documentation added on special methods
	 */
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * Method returns all members of this teamas an array of String
	 * 
	 * @return
	 * 		Integer[] members : array containing id's for all members of this team
	 */
	public List<Athlete> getMembers() {
		return members;
	}
	
	//public int getNumMembers() {
	//	return members.size();
	//}
	
	public void addMember(Athlete toAdd) {
		members.add(toAdd);
	}
	
	public void removeMember(Athlete toRemove) {
		members.remove(toRemove);
	}
	
	public boolean isMember(Athlete search) {
		return members.contains(search);
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public double getRating() {
		return rating;
	}
	
	public void rate(double rating) {
		this.rating = (this.rating + rating) / ++numRatings;
	}
	
	public long getNumRatings() {
		return numRatings;
	}
	
	public Coach getCoach() {
		return coach;
	}
	
	public void setCoach(Coach coach) {
		this.coach = coach;
	}
	
	public void removeCoach() {
		this.coach = null;
	}

}
