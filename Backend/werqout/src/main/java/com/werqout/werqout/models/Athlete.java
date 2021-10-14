package com.werqout.werqout.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;

import java.util.List;
import java.util.ArrayList;

/**
 * This class defines an ahtlete model and all its fields and functions.
 * @author JJ SchraderBachar
 */

/*
 * @Inheritance tag specifies that this superclass and its subclasses will be mapped in the same table.
 * Because of this, we need a way to tell which type we are working with. This will add a column named
 * DTYPE by default which will hold the value designated by @DiscriminatorValue.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("Athlete")
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
    @JoinTable(name = "group_members",
    		   joinColumns = @JoinColumn(name = "athlete_id"),
    		   inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<Group> groups;
    
    public Athlete(int id, String userName, String email, String password) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.groups = new ArrayList<Group>();
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
