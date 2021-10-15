package com.werqout.werqout.models;

import javax.persistence.Entity;

/**
 * This class defines an ahtlete model and all its fields and functions.
 * @author JJ SchraderBachar
 */
@Entity
public class Athlete extends User {
   /**
    * Name of the group the athlete is in. Foreign Key to groups table
    */
    public String groupName;

    public Athlete(){
        
    }
    
    public Athlete(int id,String userName, String email, String password, String groupName) {
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
    public int getId() {
        return super.getId();
    }

    @Override
    public void setId(int id) {
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
    
    
    
}
