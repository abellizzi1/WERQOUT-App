package com.werqout.werqout.tests;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;
import org.mockito.Mock;

import com.werqout.werqout.models.Team;
import com.werqout.werqout.models.GymOwner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.werqout.werqout.models.Event;

@RunWith(SpringRunner.class)
public class EventTests {

    @Test
    public void testConstructorsAndGetters() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        Date d = format.parse("01-01-2021");
        Event e = new Event(1,d,"simple event");

        assertEquals(1, e.getId());
        assertEquals(d, e.getDate());
        assertEquals("simple event", e.getDescription());
    }

    @Mock 
    Team t;

    @Mock
    GymOwner g;

    @Test
    public void testGym() throws ParseException{
    
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        Date d = format.parse("01-01-2021");
        Event e = new Event(1,d,"simple event");

        e.addTeam(t);
        assertEquals(true, e.hasTeam(t));
        e.removeTeam(t);
        assertEquals(false, e.hasTeam(t));
        g.setEvent(null);
        assertEquals(null, g.getEvent());
    }

    @Test
    public void testTeam() throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        Date d = format.parse("01-01-2021");
        Event e = new Event(1,d,"team event");

        //undefined until mr is approved
        t.setEvent(e);
        assertEquals(1, t.getEventID());
        assertEquals("team event", e.getDescription());
        assertEquals(d, e.getDate());
        
    }
    
}
