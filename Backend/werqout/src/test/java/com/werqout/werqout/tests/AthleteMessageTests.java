package com.werqout.werqout.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.werqout.werqout.models.AthleteDM;
import com.werqout.werqout.models.Athlete;
import com.werqout.werqout.models.AthleteMessage;

@RunWith(SpringRunner.class)
public class AthleteMessageTests {

  @Mock
  Athlete a1, a2;

  @Mock
  AthleteDM dm, dm2;

  @Test
  public void testConstructorsAndGetters() {
    AthleteMessage m = new AthleteMessage(a1, "Hi There!", dm);

    assertEquals(a1, m.getFrom());
    assertEquals("Hi There!", m.getData());
    assertEquals(dm, m.getDM());

    m.setData("Hello There!");
    m.setFrom(a2);
    m.setDM(dm2);

    assertEquals(a2, m.getFrom());
    assertEquals("Hello There!", m.getData());
    assertEquals(dm2, m.getDM());
  }
}
