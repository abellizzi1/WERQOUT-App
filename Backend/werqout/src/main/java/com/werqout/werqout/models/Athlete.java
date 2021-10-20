package com.werqout.werqout.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.JoinColumn;

import java.util.List;
import java.util.ArrayList;

/**
 * This class defines an ahtlete model and all its fields and functions.
 * @author JJ SchraderBachar
 */

@Entity
@Table(name = "athletes")
public class Athlete {
    /**
     * User ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * username of the athlete
     */
    private String userName;
    /**
     * email of the athelete- will be checked with REGEX
     */
    private String email;
    /**
     * Password of the athlete- hashed in DB and will be checked with REGEX
     */
    private String password;
    /**
     * Constructs an athlete
     * @param id
     * @param userName
     * @param email
     * @param password
     */
    
    /**
     * ArrayList which contains all teams athlete is a memeber of
     * Within database, represented simply as an athlete id and a team id in a table
     */
     
    @ManyToMany(fetch = FetchType.LAZY,
    			cascade = CascadeType.PERSIST)
    @JoinTable(name = "team_members",
    		   joinColumns = @JoinColumn(name = "athlete_id"),
    		   inverseJoinColumns = @JoinColumn(name = "team_id"))
    @JsonIgnore
    private List<Team> teams = new ArrayList<Team>();
    
    // Constructors ==========================================================================================
    
    public Athlete(){
        
    }
    
    public Athlete(long id,String userName, String email, String password) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
    
    // Basic Getters/Setters =================================================================================

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getEmail() {
    	return email;
    }
    
    public void setEmail(String email) {
    	this.email = email;
    }
    
    public String getPassword() {
    	return password;
    }
    
    public void setPassword(String password) {
    	this.password = password;
    }
    
    // Manage teams list =====================================================================================

    public List<Team> getGroups() {
    	return teams;
    }
    
    public void addGroup(Team group) {
    	teams.add(group);
    }
    
    public void removeGroup(Team group) {
    	teams.remove(group);
    }
}
