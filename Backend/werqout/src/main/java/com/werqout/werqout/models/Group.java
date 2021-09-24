package com.werqout.werqout.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.ArrayList;

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
	private ArrayList<String> members;
	private int numMembers;
	
	/**
	 * Object which represents the group's coach
	 * Foreign key to Athlete table
	 */
	private Athlete coach;
	
	/**
	 * String providing a general description of the group
	 */
	private String description;
	
	// TODO: Create gyms and add usual gym as an instance var
	
	/**
	 * Value represents average rating of the group, based on user reviews
	 */
	private double rating;
	
	public Group(String name, String[] members, Athlete coach, String description) {
		this.name = name;
		this.coach = coach;
		for(int i = 0; i < members.length; i++) {
			this.members.add(members[i]);
		}
		this.coach = coach;
		this.description = description;
		rating = 0;
	}
	
	public Group() {};
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String[] getMembers() {
		return (String[]) members.toArray();
	}
	
	public void addMember(String toAdd) {
		members.add(toAdd);
	}
	
	public void removeMember(String toRemove) {
		members.remove(toRemove);
	}

}
