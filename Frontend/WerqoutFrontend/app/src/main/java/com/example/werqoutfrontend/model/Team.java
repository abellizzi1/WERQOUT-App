package com.example.werqoutfrontend.model;

import java.util.ArrayList;

public class Team {
    public ArrayList<Athlete> listAthletes;
    public ArrayList<Workout> listWorkouts;
    private String teamName;

    public Team(String teamName)
    {
        this.teamName = teamName;
        listAthletes = new ArrayList<Athlete>();
        listWorkouts = new ArrayList<Workout>();
    }

    public void setTeamName(String teamName)
    {
        this.teamName = teamName;
    }

    public String getTeamName() { return teamName; }

    public void addAthlete(Athlete a)
    {
        listAthletes.add(a);
        a.setTeam(this);
    }

    public void removeAthlete(Athlete a)
    {
        listAthletes.remove(a);
        a.setTeam(null);
    }
}
