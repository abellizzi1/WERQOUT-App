package com.example.werqoutfrontend;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
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

    @Test
    public void testSearchFilter1()
    {
        FilterResults filterResults = mock(FilterResults.class);
        RecyclerViewComponent tim = new RecyclerViewComponent(R.drawable.ic_default_account_box,
                "tim", "tim@gmail.com", 0);
        RecyclerViewComponent tom = new RecyclerViewComponent(R.drawable.ic_default_account_box,
                "tom", "tom@gmail.com", 1);
        RecyclerViewComponent ron = new RecyclerViewComponent(R.drawable.ic_default_account_box,
                "Ron", "ron@gmail.com", 2);
        ArrayList<RecyclerViewComponent> searchResults = new ArrayList<>();
        searchResults.add(tim);
        searchResults.add(tom);
        searchResults.add(ron);
        FilterLogic filterLogic = new FilterLogic();
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
    @Test
    public void testSearchFilter2()
    {
        FilterResults filterResults = mock(FilterResults.class);
        RecyclerViewComponent tim = new RecyclerViewComponent(R.drawable.ic_default_account_box,
                "tim", "tim@gmail.com", 0);
        RecyclerViewComponent tom = new RecyclerViewComponent(R.drawable.ic_default_account_box,
                "tom", "tom@gmail.com", 1);
        RecyclerViewComponent ron = new RecyclerViewComponent(R.drawable.ic_default_account_box,
                "Ron", "ron@gmail.com", 2);
        ArrayList<RecyclerViewComponent> searchResults = new ArrayList<>();
        searchResults.add(tim);
        searchResults.add(tom);
        searchResults.add(ron);
        FilterLogic filterLogic = new FilterLogic();
        ArrayList<RecyclerViewComponent> actualResponse = filterLogic.performFiltering("o",searchResults);

        ArrayList<RecyclerViewComponent> filterTest1 = new ArrayList<>();
        ArrayList<RecyclerViewComponent> simulatedResponse = new ArrayList<>();
        filterTest1.add(tom);
        filterTest1.add(ron);

        when(filterResults.getFilteredList()).thenReturn(filterTest1);
        simulatedResponse.addAll(filterResults.getFilteredList());
        for(int i = 0; i < actualResponse.size(); i++)
        {
            assertSame(simulatedResponse.get(i).getText1(), actualResponse.get(i).getText1());
        }
    }
    @Test
    public void testSearchFilter3()
    {
        FilterResults filterResults = mock(FilterResults.class);
        RecyclerViewComponent tim = new RecyclerViewComponent(R.drawable.ic_default_account_box,
                "tim", "tim@gmail.com", 0);
        RecyclerViewComponent tom = new RecyclerViewComponent(R.drawable.ic_default_account_box,
                "tom", "tom@gmail.com", 1);
        RecyclerViewComponent ron = new RecyclerViewComponent(R.drawable.ic_default_account_box,
                "Ron", "ron@gmail.com", 2);
        ArrayList<RecyclerViewComponent> searchResults = new ArrayList<>();
        searchResults.add(tim);
        searchResults.add(tom);
        searchResults.add(ron);
        FilterLogic filterLogic = new FilterLogic();
        ArrayList<RecyclerViewComponent> actualResponse = filterLogic.performFiltering("r",searchResults);

        ArrayList<RecyclerViewComponent> filterTest1 = new ArrayList<>();
        ArrayList<RecyclerViewComponent> simulatedResponse = new ArrayList<>();
        filterTest1.add(ron);

        when(filterResults.getFilteredList()).thenReturn(filterTest1);
        simulatedResponse.addAll(filterResults.getFilteredList());
        for(int i = 0; i < actualResponse.size(); i++)
        {
            assertSame(simulatedResponse.get(i).getText1(), actualResponse.get(i).getText1());
        }
    }
    @Test
    public void testSearchFilter4()
    {
        FilterResults filterResults = mock(FilterResults.class);
        RecyclerViewComponent tim = new RecyclerViewComponent(R.drawable.ic_default_account_box,
                "tim", "tim@gmail.com", 0);
        RecyclerViewComponent tom = new RecyclerViewComponent(R.drawable.ic_default_account_box,
                "tom", "tom@gmail.com", 1);
        RecyclerViewComponent ron = new RecyclerViewComponent(R.drawable.ic_default_account_box,
                "Ron", "ron@gmail.com", 2);
        ArrayList<RecyclerViewComponent> searchResults = new ArrayList<>();
        searchResults.add(tim);
        searchResults.add(tom);
        searchResults.add(ron);
        FilterLogic filterLogic = new FilterLogic();
        ArrayList<RecyclerViewComponent> actualResponse = filterLogic.performFiltering("Colin",searchResults);

        ArrayList<RecyclerViewComponent> filterTest1 = new ArrayList<>();
        ArrayList<RecyclerViewComponent> simulatedResponse = new ArrayList<>();

        when(filterResults.getFilteredList()).thenReturn(filterTest1);
        simulatedResponse.addAll(filterResults.getFilteredList());
        for(int i = 0; i < actualResponse.size(); i++)
        {
            assertSame(simulatedResponse.get(i).getText1(), actualResponse.get(i).getText1());
        }
    }
}
