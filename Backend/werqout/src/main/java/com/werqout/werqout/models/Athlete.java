package com.werqout.werqout.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import java.util.List;

/**
 * This class defines an ahtlete model and all its fields and functions.
 * @author JJ SchraderBachar
 */
@Entity
public class Athlete {
    /**
     * User ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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
    
    @ManyToMany
    private List<Group> groups;
    
    public Athlete(int id, String userName, String email, String password) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
    public Athlete(){
        
    }
    /**
     * gets the User's' ID
     * @return users ID
     */
    public int getId() {
        return id;
    }
    /**
     * Sets the ID
     * @param id of the ahtlete
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * gets the user's username
     * @return userName
     */
    public String getUserName() {
        return userName;
    }
    /**
     * Sets the username
     * @param userName username of the athlete
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
     * gets the athletes email
     * @return athlete's email
     */
    public String getEmail() {
        return email;
    }
    /**
     * Sets the atheletes email
     * @param email of the athelete
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * gets the athletes Password- should be hashed to store into database
     * @return athletes hashed password
     */
    public String getPassword() {
        return password;
    }
    /**
     * Sets the athlete's password
     * @param password of the althlete
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public List<Group> getGroups() {
    	return groups;
    }
    
    public void addGroup(Group group) {
    	groups.add(group);
    }
    
    public void removeGroup(Group group) {
    	groups.remove(group);
    }
}
