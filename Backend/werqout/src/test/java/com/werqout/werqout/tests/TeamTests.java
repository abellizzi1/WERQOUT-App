package com.werqout.werqout.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.werqout.werqout.models.Coach;
import com.werqout.werqout.models.Team;
import com.werqout.werqout.models.Athlete;

@RunWith(SpringRunner.class)
public class TeamTests {

	@Test
	public void testConstructorAndGetters() {
		Team t = new Team(1, "testTeam", "testDesc");
		t.rate(5);
		
		assertEquals(1, t.getId());
		assertEquals("testTeam", t.getName());
		assertEquals("testDesc", t.getDescription());
		assertEquals(5, t.getRating(), .001);
		assertEquals(1, t.getNumRatings());
	}
	
	@Mock
	Athlete a;
	
	@Mock
	Coach c;
	
	@Test
	public void testMembers() {
		Team t = new Team(1, "testTeam", "testDesc");
		t.addMember(a);
		
		assertEquals(true, t.isMember(a));
		t.removeMember(a);
		assertEquals(false, t.isMember(a));
		
		t.setCoach(c);
		assertEquals(c, t.getCoach());
		t.removeCoach();
		assertEquals(null, t.getCoach());
	}
	
}
