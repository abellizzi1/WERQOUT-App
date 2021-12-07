package com.example.werqoutfrontend;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.werqoutfrontend.model.Coach;
import com.example.werqoutfrontend.model.GymOwner;
import com.example.werqoutfrontend.utils.CalculateRating;

@RunWith(MockitoJUnitRunner.class)
public class RatingTests {

    private Coach colin;
    private Coach testCoach;
    private GymOwner testGymOwner;
    @Before public void initialize()
    {
        colin = new Coach("colinbrenizer@gmail.com","pass", "colin", 4
        ,0,0);
        testCoach = mock(Coach.class);
        testGymOwner = mock(GymOwner.class);

    }

    @Test
    public void testCoachRating()
    {
        when(testCoach.getRating()).thenReturn(0.0);
        CalculateRating calculate = new CalculateRating();
        double newRating = calculate.calc(testCoach.getRating(),1,1);
        newRating = calculate.calc(newRating, 5, 2);
        newRating = calculate.calc(newRating, 3, 2);
        when(testCoach.getRating()).thenReturn(3.0);
        assertEquals(newRating,testCoach.getRating(),.01);
    }

    @Test
    public void testGymOwnerRating()
    {
        when(testGymOwner.getRating()).thenReturn(3.0);
        CalculateRating calculate = new CalculateRating();
        double newRating = calculate.calc(testGymOwner.getRating(),4,2);
        newRating = calculate.calc(newRating, 5, 2);
        newRating = calculate.calc(newRating, 5, 2);
        when(testGymOwner.getRating()).thenReturn(4.625);
        assertEquals(newRating,testGymOwner.getRating(),.01);

    }
}
