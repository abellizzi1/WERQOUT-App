package com.werqout.werqout.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.werqout.werqout.models.AthleteDM;
import com.werqout.werqout.models.Athlete;
import com.werqout.werqout.models.AthleteMessage;

import java.util.List;

@RunWith(SpringRunner.class)
public class AthleteDMTests {

    @Mock
    Athlete athlete1, athlete2;

    AthleteMessage m1, m2, m3;

    @Test
    public void testConstructorsAndGetters() {
        AthleteDM a = new AthleteDM(athlete1, athlete2);

        assertEquals(athlete1, a.getAthlete1());
        assertEquals(athlete2, a.getAthlete2());

        a.setAthlete1(athlete2);
        a.setAthlete2(athlete1);

        assertEquals(athlete2, a.getAthlete1());
        assertEquals(athlete1, a.getAthlete2());
    }

   @Test
   public void testSendMessages() {
       AthleteDM a = new AthleteDM(athlete1, athlete2);

       m1 = new AthleteMessage();
       m2 = new AthleteMessage();

       m1.setFrom(athlete1);
       m2.setFrom(athlete2);
       a.sendMessage(m1);
       a.sendMessage(m2);

       List<AthleteMessage> dms = a.getMessages();
       assertTrue(dms.contains(m1));
       assertTrue(dms.contains(m2));
       assertFalse(dms.contains(m3));
   }
}
