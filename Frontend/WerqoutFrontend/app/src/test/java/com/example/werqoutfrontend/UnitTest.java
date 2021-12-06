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

import com.example.werqoutfrontend.utils.filters.FilterLogic;
import com.example.werqoutfrontend.utils.filters.FilterResults;
import com.example.werqoutfrontend.utils.RecyclerViewComponent;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class UnitTest {

    private FilterResults filterResults;
    private ArrayList<RecyclerViewComponent> searchResults;
    private FilterLogic filterLogic;

    private RecyclerViewComponent tim;
    private RecyclerViewComponent tom;
    private RecyclerViewComponent ramona;

    @Before public void initialize()
    {
        filterResults = mock(FilterResults.class);
        tim = new RecyclerViewComponent(R.drawable.ic_default_account_box,
                "tim", "tim@gmail.com", 0);
        tom = new RecyclerViewComponent(R.drawable.ic_default_account_box,
                "tom", "tom@gmail.com", 1);
        ramona = new RecyclerViewComponent(R.drawable.ic_default_account_box,
                "Ramona", "ramona@gmail.com", 2);
        searchResults = new ArrayList<>();
        searchResults.add(tim);
        searchResults.add(tom);
        searchResults.add(ramona);
        filterLogic = new FilterLogic();
    }
    /*
    This first test simulates typing the letter t into the search bar. What should happen is that
    the filter should return a list containing tim and tom, because ron does not have the letter t
    in it.
     */
    @Test
    public void testSearchFilter1()
    {
        ArrayList<RecyclerViewComponent> actualResponse = filterLogic.performFiltering("t",searchResults);

        ArrayList<RecyclerViewComponent> filterTest1 = new ArrayList<>();
        ArrayList<RecyclerViewComponent> simulatedResponse = new ArrayList<>();
        filterTest1.add(tim);
        filterTest1.add(tom);

        when(filterResults.getFilteredList()).thenReturn(filterTest1);
        simulatedResponse.addAll(filterResults.getFilteredList());
        for(int i = 0; i < actualResponse.size(); i++)
        {
            assertSame(simulatedResponse.get(i).getText1(), actualResponse.get(i).getText1());
        }
    }
    /*
    Test 2 simulates entering the letter m into the search query. Since every user has the letter
    m in their username, the filter should return a list that contains every user.
     */
    @Test
    public void testSearchFilter2()
    {
        ArrayList<RecyclerViewComponent> actualResponse = filterLogic.performFiltering("m",searchResults);

        ArrayList<RecyclerViewComponent> filterTest1 = new ArrayList<>();
        ArrayList<RecyclerViewComponent> simulatedResponse = new ArrayList<>();
        filterTest1.add(tim);
        filterTest1.add(tom);
        filterTest1.add(ramona);

        when(filterResults.getFilteredList()).thenReturn(filterTest1);
        simulatedResponse.addAll(filterResults.getFilteredList());
        for(int i = 0; i < actualResponse.size(); i++)
        {
            assertSame(simulatedResponse.get(i).getText1(), actualResponse.get(i).getText1());
        }
    }
    /*
    This test simulates entering r into the search query. Since ramona is the only user that has
    the letter r in their username, the filter should return a list that only contains ramona.
     */
    @Test
    public void testSearchFilter3()
    {
        ArrayList<RecyclerViewComponent> actualResponse = filterLogic.performFiltering("r",searchResults);

        ArrayList<RecyclerViewComponent> filterTest1 = new ArrayList<>();
        ArrayList<RecyclerViewComponent> simulatedResponse = new ArrayList<>();
        filterTest1.add(ramona);

        when(filterResults.getFilteredList()).thenReturn(filterTest1);
        simulatedResponse.addAll(filterResults.getFilteredList());
        for(int i = 0; i < actualResponse.size(); i++)
        {
            assertSame(simulatedResponse.get(i).getText1(), actualResponse.get(i).getText1());
        }
    }
    /*
    This test simulates entering a username that does not exist. The filter should return an empty list.
     */
    @Test
    public void testSearchFilter4()
    {
        ArrayList<RecyclerViewComponent> actualResponse = filterLogic.performFiltering("zach",searchResults);

        ArrayList<RecyclerViewComponent> filterTest1 = new ArrayList<>();
        ArrayList<RecyclerViewComponent> simulatedResponse = new ArrayList<>();

        when(filterResults.getFilteredList()).thenReturn(filterTest1);
        simulatedResponse.addAll(filterResults.getFilteredList());
        assertTrue(actualResponse.size() == simulatedResponse.size());
    }
}
