package com.example.werqoutfrontend;

import static org.mockito.Mockito.*;

import com.example.werqoutfrontend.model.Athlete;
import com.example.werqoutfrontend.model.GymOwner;
import com.example.werqoutfrontend.model.Team;
import com.example.werqoutfrontend.model.Event;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

/*
test:
when a team is rated:
get the rating of the team

when a gym owner adds a team:
get the list of the gym owner's teams at their gym
 */

public class RatingsAndGymOwnerTest {
    ManageGymTeamsScreen mgts = mock(ManageGymTeamsScreen.class);
    ArrayList<Team> testTeamsList = new ArrayList<Team>();
    Team testTeam = new Team("databaseTeam1");
    AthleteMyGroupScreen amgs = mock(AthleteMyGroupScreen.class);

    @Test
    public void testAddTeamToGym()
    {
        testTeamsList.add(testTeam);
        GymOwner gymOwner = new GymOwner("Angelo", "angelo@iastate.edu", "Angelo123!", "Gym Owner", 1);
        when(mgts.getGymTeams(gymOwner)).thenReturn(testTeamsList);
        gymOwner.listTeams = mgts.getGymTeams(gymOwner);
        Team t = new Team("newTeam");
        gymOwner.addTeam(t);
        assertEquals(t.getTeamGymOwner(), gymOwner);
        assertEquals(2, gymOwner.listTeams.size());
    }

    @Test
    public void testTeamRatings()
    {
        Team t2 = new Team("newTeam2");
        t2.rate(4);
        when(amgs.getRating(t2)).thenReturn(2);
        t2.rate(amgs.getRating(t2));
        assertEquals(t2.numRatings, 2);
        assertEquals((int)t2.getRating(), 3);
    }

}
