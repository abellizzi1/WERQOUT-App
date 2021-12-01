package com.werqout.werqout.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

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
    @ApiModelProperty(notes = "Id of the Athlete",name="id",required=true,value="1")
	private long id;
    /**
     * Date that the event takes place
     */
    @ApiModelProperty(notes = "Date the event takes place",name="date",required=true,value="12-12-2021:00:00")
    private Date date;
    /**
     * Gym Owner that hosts the workout
     */
    @OneToOne
    @JsonIgnore
    @JoinTable(name="gym",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name="gym_owner_id"))
    @ApiModelProperty(notes = "Owner of the gym that the event takes place",name="go",required=true,value="Gym 01")
    private GymOwner go;
    /**
     * Description of the event- what the group is going to do.
     */
    @ApiModelProperty(notes = "Description of the event",name="description",required=true,value="Push Pull Legs week 1 day 3")
    private String description;
    /**
     * Team at this specific time 
     */
    @ManyToMany
    @JsonIgnore
    @JoinTable(name="gym",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name="team_id"))
    @ApiModelProperty(notes = "Teams that are attending the event",name="teams",required=false,value="Team1, Team2")
    private List<Team> teams = new ArrayList<Team>();
    
    /**
     * Creates an event
     * @param id  id of the event
     * @param date date the event takes place- yyyy/mm/dd 00:00 format
     * @param gymOwner  gymOwner that is hosting the event
     * @param desc  description of the event
     * @param team  team that is attending this particular event
     */
    public Event(long id, Date date, String desc) {
        this.id = id;
        this.date = date;
        this.description = desc;
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
    public void setId(long id) {
    	this.id = id;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    @JsonIgnore
    public GymOwner getGymOwner() {
        return go;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<Team> getTeams() {
        return teams;
    }
    public void addTeam(Team team){
        teams.add(team);
    }
    public boolean hasTeam(Team team){
        return teams.contains(team);
    }
    public void removeTeam(Team team) {
        teams.remove(team);
    }
   
   
}
