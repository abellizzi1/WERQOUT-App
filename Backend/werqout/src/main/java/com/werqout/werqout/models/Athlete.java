package com.werqout.werqout.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

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
    @ApiModelProperty(notes = "Id of the Athlete",name="id",required=true,value="1")
    private long id;
    /**
     * username of the athlete
     */
    @ApiModelProperty(notes = "Username of the Athlete",name="userName",required=true,value="testUserName")
    private String userName;
    /**
     * email of the athelete- will be checked with REGEX
     */
    @ApiModelProperty(notes = "Email of the Athlete",name="email",required=true,value="email@example.com")
    private String email;
    /**
     * Password of the athlete- hashed in DB and will be checked with REGEX
     */
    @ApiModelProperty(notes = "Password of the Athlete",name="password",required=true,value="testpassword")
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
    		   inverseJoinColumns = @JoinColumn(name = "group_id"))
    @JsonIgnore
    private List<Team> teams = new ArrayList<Team>();
    
    @OneToMany
    @JsonIgnore
    @JoinTable(name="athlete_dms",
    		   joinColumns = @JoinColumn(name = ""))
    private List<AthleteDM> dms = new ArrayList<>();
    
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

    public List<Team> getTeams() {
    	return teams;
    }
    
    public void addTeam(Team team) {
    	teams.add(team);
    }
    
    public void removeTeam(Team team) {
    	teams.remove(team);
    }
    
    // Manage DMs
    
    public List<AthleteDM> getOpenDMs(){
    	return dms;
    }
    
    public void addDM(AthleteDM dm) {
    	dms.add(dm);
    }
    
    public void removeDM(AthleteDM dm) {
    	dms.remove(dm);
    }
    
    public void sendDM(AthleteDM dm, String message) {
    	dm.sendMessage(this, message);
    }
}
