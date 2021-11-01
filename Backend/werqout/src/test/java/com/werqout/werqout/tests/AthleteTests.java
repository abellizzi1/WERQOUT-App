package com.werqout.werqout.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.werqout.werqout.models.Athlete;
import com.werqout.werqout.models.Team;

@RunWith(SpringRunner.class)
public class AthleteTests {
	
	@Mock
	Team t;

	@Test 
	public void testConstructorAndGetters() {
		Athlete a = new Athlete(1, "testName", "testEmail", "testPass");
		
		assertEquals(1, a.getId());
		assertEquals("testName", a.getUserName());
		assertEquals("testEmail", a.getEmail());
		assertEquals("testPass", a.getPassword());
	}
	
	@Test
	public void testTeams() {
		Athlete a = new Athlete();
		a.addTeam(t);
		
		assertEquals(true, a.getTeams().contains(t));
		a.removeTeam(t);
		
		assertEquals(false, a.getTeams().contains(t));
	}
}
