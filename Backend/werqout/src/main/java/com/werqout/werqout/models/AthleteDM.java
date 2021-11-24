package com.werqout.werqout.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
	private long id;
	
	// Id of first athlete in relationship
	@ManyToOne
	private Athlete athlete1;
	
	// Id of second athlete in relationship
	@ManyToOne
	private Athlete athlete2;
	
	// Stack which contains messages between these users
	@Column(name = "messages")
	private Stack<AthleteMessage> messages = new Stack<>();
	
	public AthleteDM(Athlete athlete1, Athlete athlete2) {
		this.athlete1 = athlete1;
		this.athlete2 = athlete2;
	}
	
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
	public void sendMessage(Athlete from, String data) {
		
		// Check that the from and to parameters both represent an athlete in the relationship
		
		if(from == athlete1) {
			AthleteMessage message = new AthleteMessage(from, data);
			messages.push(message);
		} else if(from == athlete2) {
			AthleteMessage message = new AthleteMessage(from, data);
			messages.push(message);
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
		for(int i = 0; i < numToRetrieve; i++) {
			toReturn.add(messages.get(i));
		}
		return toReturn;
	}
	
}
