package com.werqout.werqout.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Athlete extends User {
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
    
    /**
     * Name of the group the athlete is in. Foreign Key to groups table
     */
     public String groupName;
     
    @ManyToMany(fetch = FetchType.LAZY,
    			cascade = CascadeType.PERSIST)
    @JoinTable(name = "group_members",
    		   joinColumns = @JoinColumn(name = "athlete_id"),
    		   inverseJoinColumns = @JoinColumn(name = "group_id"))
    @JsonIgnore
    private List<Group> groups = new ArrayList<Group>();
    


    public Athlete(){
        
    }
    
    public Athlete(long id,String userName, String email, String password, String groupName) {
        super(id, userName, email, password);
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public long getId() {
        return super.getId();
    }

    @Override
    public void setId(long id) {
        super.setId(id);
    }

    @Override
    public String getUserName() {
        return super.getUserName();
    }
    
    @Override
    public void setUserName(String userName) {
        super.setUserName(userName);
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
