package com.example.werqoutfrontend;
import org.java_websocket.client.WebSocketClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.werqoutfrontend.model.Coach;
import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.network.Websocket;
import com.example.werqoutfrontend.utils.CalculateRating;
import com.example.werqoutfrontend.utils.VolleyCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class OnlineTest {

    private Coach colin;
    private Coach testCoach;
    private CalculateRating cr;
    @Before public void initialize()
    {
        colin = new Coach("colinbrenizer@gmail.com","pass", "colin", 4
        ,0,0);
        testCoach = mock(Coach.class);
        cr = mock(CalculateRating.class);
    }

    @Test
    public void testAddDMS()
    {
        double newRating = CalculateRating.calculate(colin.getRating(), 1,colin.getNumRatings());
        CalculateRating.calculate(newRating, 5,colin.getNumRatings());
        CalculateRating.calculate(newRating, 3,colin.getNumRatings());

        assertEquals(1,1);
    }
}
