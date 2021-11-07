package com.werqout.werqout.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.werqout.werqout.models.Coach;
import com.werqout.werqout.models.Team;

@RunWith(SpringRunner.class)
public class CoachTests {
	
	@Test
	public void testConstructorAndGetters() {
		Coach c = new Coach(1, "testName", "testEmail", "testPass", 0, 0);
		
		assertEquals(1, c.getId());
		assertEquals("testName", c.getUserName());
		assertEquals("testEmail", c.getEmail());
		assertEquals("testPass", c.getPassword());
		assertEquals(5, c.rate(5), .001);
		assertEquals(1, c.getNumRatings());
	}
	
	@Mock
	private Team t;
	
	@Test
	public void testManagedTeam() {
		Coach c = new Coach(1, "testName", "testEmail", "testPass", 0, 0);
		
		assertEquals(null, c.getManagedTeam());
		c.setManagedTeam(t);
		assertEquals(t, c.getManagedTeam());
		c.removeManagedTeam();
		assertEquals(null, c.getManagedTeam());
	}

}
