package com.werqout.werqout.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.List;

@Entity
public class Group {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	/**
	 * Name of the group 
	 */
	private String name;
	
	/**
	 * Array which holds members of the group
	 * Foreign key for Athlete table
	 */
	@OneToMany
	private List<Athlete> members;
	
	/**
	 * Object which represents the group's coach
	 * Foreign key to Athlete table
	 */
	// TODO: add coach dependency once coach class is working
	//@ManyToOne
	//private int coach;
	
	/**
	 * String providing a general description of the group
	 */
	private String description;
	
	// TODO: Create gyms and add usual gym as an instance var
	
	/**
	 * Value represents average rating of the group, based on user reviews
	 */
	private double rating;
	private int numRatings;
	
	
	/**
	 * Create a new group and set key parameters
	 * 
	 * @param name
	 * 		String name			: Name of group to be created
	 * 
	 * @param members
	 * 		int[] members		: Array of ints which are keys to the athlete table, to be added to members list
	 * 
	 * @param coach
	 * 		int coach			: Int which is a key to the athlete table, sets this as group's coach
	 * 
	 * @param description
	 * 		String description:	: String to be group's general description
	 */
	public Group(String name, Athlete[] members, int coach, String description) {
		this.name = name;
		
		// Iterate through members argument, add each member to members ArrayList
		for(int i = 0; i < members.length; i++) {
			this.members.add(members[i]);
		}
		this.description = description;
		
		// Set rating to 0, numRatings to 0 by default
		rating = 0;
		numRatings = 0;
	}
	
	/**
	 * Default constructor for group, only variable defined is rating and numRatings
	 */
	public Group() {
		this.rating = 0;
		this.numRatings = 0;
	}
	
	/*
	 * General getters and setters, documentation added on special methods
	 */
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * Method returns all members of this group as an array of String
	 * 
	 * @return
	 * 		Integer[] members : array containing id's for all members of this group
	 */
	public List<Athlete> getMembers() {
		return members;
	}
	
	public int getNumMembers() {
		return members.size();
	}
	
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

}
