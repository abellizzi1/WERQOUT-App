package com.werqout.werqout.models;

import javax.persistence.Entity;

/**
 * This class defines an ahtlete model and all its fields and functions.
 * @author JJ SchraderBachar
 */
@Entity
public class Athlete extends User {
   /**
    * Name of the team the athlete is in. Foreign Key to team table
    */
    public String teamName;

    public Athlete(){
        
    }
    
    public Athlete(long id,String userName, String email, String password, String teamName) {
        super(id, userName, email, password);
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
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
    
    
    
}
