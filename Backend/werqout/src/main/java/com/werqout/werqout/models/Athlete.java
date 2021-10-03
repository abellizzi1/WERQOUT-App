package com.werqout.werqout.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    
    public Athlete(long id,String userName, String email, String password, String groupName) {
        super(id, userName, email, password);
        this.groupName = groupName;
    }

    
}
