package com.example.werqoutfrontend.utils.filters;

import com.example.werqoutfrontend.utils.RecyclerViewComponent;

import java.util.ArrayList;

/**
 * Basically calls the filter logic class and returns the filter results
 */

public class FilterResults {
    /**
     * Returns the filter results
     * @return
     *  Filter results
     */
    public ArrayList<RecyclerViewComponent> getFilteredList()
    {
        FilterLogic f = new FilterLogic();
        String constraint = "";
        ArrayList <RecyclerViewComponent> e = new ArrayList<>();
        return f.performFiltering(constraint, e);
    }
}
