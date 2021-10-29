package com.werqout.werqout.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * This class is to represent an event. It has a time it takes place, a group, and a gym owner that hosts it. Ideally the gym owner posts the event time and the coach selects that time with a specific group
 */
@Entity
@Table(name="events")
public class Event {
    /**
     * Id of the event
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    /**
     * Date that the event takes place
     */
    private Date date;
    /**
     * Gym Owner that hosts the workout
     */
    @OneToMany
    @JsonIgnore
    @JoinTable(name="gym",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name="gym_owner_id"))
    private GymOwner go;
    /**
     * Description of the event- what the group is going to do.
     */
    private String description;
    /**
     * Team at this specific time 
     */
    @OneToMany
    @JsonIgnore
    @JoinTable(name="gym",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name="team_id"))
    private Team team;
    
    /**
     * Creates an event
     * @param id  id of the event
     * @param date date the event takes place- mm/dd/yyyy 00:00 format
     * @param gymOwner  gymOwner that is hosting the event
     * @param desc  description of the event
     * @param team  team that is attending this particular event
     */
    public Event(long id, Date date, GymOwner gymOwner, String desc, Team team) {
        this.id = id;
        this.date = date;
        this.go = gymOwner;
        this.description = desc;
        this.team = team;
    }
    /**
     * Default constructor
     */
    public Event(){

    }
    // getters and setters
    public long getId() {
        return id;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public GymOwner getGymOwner() {
        return go;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Team getTeam() {
        return team;
    }
    public void setTeam(Team team) {
        this.team = team;
    }
   
   
}
