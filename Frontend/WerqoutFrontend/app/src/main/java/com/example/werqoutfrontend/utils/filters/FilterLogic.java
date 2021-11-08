package com.example.werqoutfrontend.utils.filters;

import android.widget.Filter;

import com.example.werqoutfrontend.utils.RecyclerViewComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the filtering logic that is used in for the search functionality. Basically iterates through
 * a list to see if the list element contain a certain character sequence, if it doesn't contain the character
 * sequence then it is removed from the list.
 */
public class FilterLogic {
    /**
     * Performs a filtering operation on a list of strings
     * @param constraint
     *  The constraint by which the list is filtered by
     * @param fullList
     *  The full original list
     * @return
     *  The filtered list
     */
    public ArrayList<RecyclerViewComponent> performFiltering(CharSequence constraint, ArrayList<RecyclerViewComponent>fullList) {
        ArrayList<RecyclerViewComponent> filteredList = new ArrayList<>();
        if(constraint == null || constraint.length() == 0){
            filteredList.addAll(fullList);
        } else{
            String filterPattern =constraint.toString().toLowerCase().trim();
            for(RecyclerViewComponent item: fullList){
                if(item.getText1().toLowerCase().contains(filterPattern))
                {
                    filteredList.add(item);
                }
            }
        }
        return filteredList;
    }
}
