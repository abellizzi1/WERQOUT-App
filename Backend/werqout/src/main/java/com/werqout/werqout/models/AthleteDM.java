package com.werqout.werqout.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.werqout.werqout.repository.AthleteMessageRepository;

/**
 * @author evanu
 *
 * This class defines a DM relationship/history between two Athlete users.
 * We have 2 id's that define which athletes are in this relationship.
 * Messages are stored in a stack as the object AthleteMessage
 */

@Entity
public class AthleteDM {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	// List of athletes in this dm, generally two
	@ManyToMany(mappedBy = "dms")
	@JsonIgnore
	private List<Athlete> athletes = new ArrayList<Athlete>();
	
	// List of messages between these two athletes. Highest index is most recent
	@OneToMany
	@JoinTable(name = "athlete_messages",
			   joinColumns = @JoinColumn(name = "dm_id"),
			   inverseJoinColumns = @JoinColumn(name = "message_id"))
	private List<AthleteMessage> messages = new ArrayList<AthleteMessage>();
	
	public AthleteDM(Athlete athlete1, Athlete athlete2) {
		athletes.add(athlete1);
		athletes.add(athlete2);
	}
	
	public AthleteDM() {}
	
	/**
	 * Adds a message to this relationship by pushing it to the messages stack
	 * 
	 * @param from
	 *   id of athlete which is sending the message
	 * @param to
	 *   id of athlete which the message is being sent to
	 * @param data
	 * 	 String which represents the actual message to be sent
	 */
	public void sendMessage(AthleteMessage message) {
		
		// Check that the from and to parameters both represent an athlete in the relationship
		
		if(message.getFrom() == athletes.get(0)) {
			messages.add(message);
		} else if(message.getFrom() == athletes.get(1)) {
			messages.add(message);
		} else {
		// If not, illegal argument
		throw new IllegalArgumentException();
		}
		
	}
	
	/**
	 * Gets the last 20 messages between the athletes (or less if there are less than 20 total)
	 * 
	 * @return
	 *   List containing the last 20 messages
	 */
	public List<AthleteMessage> getRecent(){
		List<AthleteMessage> toReturn = new ArrayList<>();
		
		int numToRetrieve = 20;
		// Avoid index out of bounds, if stack.size() < 20, we will retrieve stack.size() messages
		if(messages.size() < 20) {
			numToRetrieve = messages.size();
		}
		
		// Iterate through stack getting messages, adding each to a list to be returned
		for(int i = numToRetrieve - 1; i >= 0; i--) {
			toReturn.add(messages.get(i));
		}
		return toReturn;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Athlete getAthlete1() {
		return athletes.get(0);
	}
	
	public Athlete getAthlete2() {
		return athletes.get(1);
	}
	
	public void setAthlete1(Athlete athlete) {
		if(athletes.get(0) == null)
			athletes.add(0, athlete);
		else
			athletes.set(0, athlete);
	}
	
	public void setAthlete2(Athlete athlete) {
		if(athletes.get(1) == null)
			athletes.add(1, athlete);
		else
			athletes.set(1, athlete);
	}
}
