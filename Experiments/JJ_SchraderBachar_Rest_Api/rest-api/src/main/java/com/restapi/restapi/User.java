package com.restapi.restapi;

public class User {
    /**
     * userName of the user
     */
    private String userName;
    /**
     * email of the user
     */
    private String email;
    /**
     * member type of the user.
     */
    private String memberType;
    
    public User(String userName, String email, String memberType) {
        this.userName = userName;
        this.email = email;
        this.memberType = memberType;
    }
    
    /**
     * Gets the username
     * @return userName
     */
    public String getUserName() {
        return userName;
    }
    /**
     * Sets the username to be used in the app
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
     * Gets the users email
     * @return email address of the user
     */
    public String getEmail() {
        return email;
    }
    /**
     * Sets the users email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Gets the type of user it is. (Athlete, Coach, Gym Owner)
     * @return type of user
     */
    public String getMemberType() {
        return memberType;
    }
    /**
     * Sets the type of user it is. (Member, Coach, Gym Owner)
     * @param memberType
     */
    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }
    
}
