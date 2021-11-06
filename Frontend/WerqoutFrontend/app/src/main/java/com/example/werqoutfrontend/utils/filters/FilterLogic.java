package com.example.werqoutfrontend.utils.filters;

import android.widget.Filter;

import com.example.werqoutfrontend.utils.RecyclerViewComponent;

import java.util.ArrayList;
import java.util.List;

public class FilterLogic {
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
