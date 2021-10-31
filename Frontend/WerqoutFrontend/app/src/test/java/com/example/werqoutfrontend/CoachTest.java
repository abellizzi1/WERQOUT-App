package com.example.werqoutfrontend;

import static org.mockito.Mockito.*;

import com.example.werqoutfrontend.model.Athlete;
import com.example.werqoutfrontend.model.Team;
import com.example.werqoutfrontend.model.Workout;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/*
when coach adds athlete:
check if athlete is in group

all on editGroupScreen, as it displays the group athletes.
there is an array of athletes that gets the athletes from the database.
when gets from database, thenReturn empty array
when the addAthlete method is called, it would add to the array
check if the created athlete is in the array

when coach deletes athlete:
check if athlete is not in group

when coach adds workout:
check if workout is posted

when coach changes name of group:
check if name changed

in the order of:
add athlete
add workout
change name
remove athlete

test all from athlete's perspective ^
 */

public class CoachTest {

    GroupInfoScreen gfs = mock(GroupInfoScreen.class);
    ArrayList<Athlete> testAthletesList = new ArrayList<Athlete>();
    Athlete testAthlete1 = new Athlete("test1@iastate.edu", "Password1!", "Test1");
    ArrayList<Workout> testWorkoutsList = new ArrayList<Workout>();
    Workout testWorkout1 = new Workout("Chest/Tri", "10/07/21", "10:00 AM");

    @Test
    public void testAddAthlete()
    {
        testAthletesList.add(testAthlete1);
        Team team = new Team("group1");
        when(gfs.getAthletes(team)).thenReturn(testAthletesList);
        team.listAthletes = gfs.getAthletes(team);
        Athlete ath = new Athlete("angelo@iastate.edu", "Angelo123!", "Angelo");
        team.addAthlete(ath);
        assertEquals(ath.getTeam(), team);
        assertEquals(2, team.listAthletes.size());
    }

    @Test
    public void testAddWorkout()
    {
        testWorkoutsList.add(testWorkout1);
        Team team = new Team("group2");
        when (gfs.getWorkouts(team)).thenReturn(testWorkoutsList);
        team.listWorkouts = gfs.getWorkouts(team);
        Workout wkt = new Workout("Back/Bi", "10/08/21", "10:00 AM");
        team.listWorkouts.add(wkt);
        team.addAthlete(testAthlete1);
        assertEquals(wkt, testAthlete1.getTeam().listWorkouts.get(1));
        assertEquals(2, team.listWorkouts.size());
    }

    @Test
    public void testChangeWorkout()
    {
        Team team = new Team("group3");
        team.addAthlete(testAthlete1);
        testWorkoutsList.add(testWorkout1);
        when (gfs.getWorkouts(team)).thenReturn(testWorkoutsList);
        team.listWorkouts = gfs.getWorkouts(team);
        assertEquals(testWorkout1, testAthlete1.getTeam().listWorkouts.get(0));
        testWorkout1.setDate("11/21/21");
        testWorkout1.setName("Legs");
        testWorkout1.setTime("7:00 PM");
        assertEquals(testWorkout1, testAthlete1.getTeam().listWorkouts.get(0));
    }

    @Test
    public void testRemoveAthlete()
    {
        testAthletesList.add(testAthlete1);
        Team team = new Team("group4");
        when(gfs.getAthletes(team)).thenReturn(testAthletesList);
        team.listAthletes = gfs.getAthletes(team);
        testAthlete1.setTeam(team);
        assertEquals(testAthlete1.getTeam(), team);
        assertEquals(1, team.listAthletes.size());
        team.removeAthlete(testAthlete1);
        assertEquals(testAthlete1.getTeam(), null);
        assertEquals(0, team.listAthletes.size());
    }

}
