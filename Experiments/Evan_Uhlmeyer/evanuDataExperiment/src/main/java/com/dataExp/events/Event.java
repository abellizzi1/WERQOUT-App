package com.dataExp.events;

/**
 * Event object definition, to be used in EventController
 * 
 * @author evanu
 *
 */

public class Event {
	
	private String name;
	private String location;
	private String description;
	
	private int startTime;
	private int endTime;
	
	
	public Event() {}
	
	public Event(String name, String location, int startTime, int endTime, String description) {
		this.name = name;
		this.location = location;
		this.startTime = startTime;
		this.endTime = endTime;
		this.description = description;
	}
	
	
	// Getters
	public String getName() {
		return name;
	}
	
	public String getLocation() {
		return location;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getStartTime() {
		return startTime;
	}
	
	public int getEndTime() {
		return endTime;
	}
	
	
	//Setters
	public void setName(String name) {
		this.name = name;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	
	
	public String toString() {
		return name      + ' ' + location + ' ' + 
			   startTime + ' ' + endTime  + ' ' +
			   description;
			   
	}
}
