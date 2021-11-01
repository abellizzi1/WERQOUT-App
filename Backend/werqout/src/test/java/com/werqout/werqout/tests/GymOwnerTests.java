package com.werqout.werqout.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.werqout.werqout.models.Team;
import com.werqout.werqout.models.GymOwner;

@RunWith(SpringRunner.class)
public class GymOwnerTests {
	
	@Test
	public void testConstructorAndGetters() {
		GymOwner g = new GymOwner(1, "testName", "testEmail", "testPass", "testGym");
		g.rate(5);
		
		assertEquals(1, g.getId());
		assertEquals("testName", g.getUserName());
		assertEquals("testEmail", g.getEmail());
		assertEquals("testPass", g.getPassword());
		assertEquals("testGym", g.getGymName());
		assertEquals(5, g.getRating(), .001);
		assertEquals(1, g.getNumRatings());
	}
	
	@Mock
	Team t;
	
	@Test
	public void testTeams() {
		GymOwner g = new GymOwner(1, "testName", "testEmail", "testPass", "testGym");
		
		g.addTeam(t);
		assertEquals(true, g.isInTeam(t));
		assertEquals(true, g.getTeams().contains(t));
		g.removeTeam(t);
		assertEquals(false, g.isInTeam(t));
	}

}
