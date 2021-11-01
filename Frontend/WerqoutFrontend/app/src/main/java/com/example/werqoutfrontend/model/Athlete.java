package com.example.werqoutfrontend.model;

public class Athlete extends User{
    private String userName;
    private String email;
    private String password;
    private Team team;

    public Athlete(String email, String password, String username, int id)
    {
        super(username, email, password, "Athlete", id);
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

    public void setTeam(Team team) { this.team = team; };

    public Team getTeam() { return team; };
}
