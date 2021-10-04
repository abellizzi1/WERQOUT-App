package com.werqout.werqout.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import java.util.List;


// @DescriminatorValue determines how the type is named in the table
@Entity
@DiscriminatorValue("Coach")
public class Coach extends Athlete {

	private double rating;
	private int numRatings;
	
	@OneToMany
	private List<Group> managedGroups;
	
	// TODO: Add "usual gym" once gym class has been implemented
	
	public Coach(int id, String userName, String email, String password) {
		super(id, userName, email, password);
		this.rating = 0;
		this.numRatings = 0;
	}
	
	public Coach() {
		super();
	}
	
	public double getRating() {
		return rating;
	}
	
	public int getNumRatings() {
		return numRatings;
	}
	
	public void rate(int rating) {
		this.rating = (this.rating + rating) / ++numRatings;
	}
	
	public List<Group> getManagedGroups(){
		return managedGroups;
	}
	
	public void addGroup(Group group) {
		managedGroups.add(group);
	}
	
	public void removeGroup(Group group) {
		managedGroups.remove(group);
	}
	
}
