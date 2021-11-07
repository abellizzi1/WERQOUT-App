package com.example.werqoutfrontend.model;


import java.io.Serializable;

/**
 * Models the user type of athlete. An Athlete can have a username, password, email, and a team
 */

@SuppressWarnings("serial")
public class Athlete extends User implements Serializable {
    /**
     * The username of the athlete
     */
    private String userName;
    /**
     * The email of the athlete
     */
    private String email;
    /**
     * The password of the athlete
     */
    private String password;
    /**
     * The team of the athlete
     */
    private Team team;

    /**
     * A constructor for an athlete. Creates a new instance of athlete with the following parameters
     * @param email
     *  The email of the athlete
     * @param password
     *  The password of the athlete
     * @param username
     *  The username of the athlete
     * @param id
     *  A unique id that defines a user
     */
    public Athlete(String email, String password, String username, int id)
    {
        super(username, email, password, "Athlete", id);
    }

    /**
     * Gets the username of the athlete
     * @return
     *  The username of the athlete
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Updates the username of the athlete
     * @param userName
     *  The new username for the athlete
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Returns the email of the athlete
     * @return
     *  The email of the athlete
     */
    public String getEmail() {
        return email;
    }

    /**
     * Updates the email of the athlete
     * @param email
     *  The new email for the athlete
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password of the user
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Updates the password of the user
     * @param password
     *  The new password for the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Updates the current team that the athlete is a part of
     * @param team
     *  The new team for the athlete
     */
    public void setTeam(Team team) { this.team = team; };

    /**
     * Returns the current team that the athlete is on
     * @return
     *  The team that the athlete is a part of
     */
    public Team getTeam() { return team; };
}
