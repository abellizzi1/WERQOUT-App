package com.example.werqoutfrontend.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.werqoutfrontend.R;

import java.util.ArrayList;
import java.util.List;

/**
 * This class encapsulates a Recycler View Adapter with filterable functionality.
 * @author Colin
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.ExampleViewHolder> implements Filterable {
    /**
     * The current list of components in the adapter
     */
    private ArrayList<RecyclerViewComponent> mExampleList;
    /**
     * The initial full list of components in the adapter
     */
    private ArrayList<RecyclerViewComponent> fullList;
    /**
     * An on click listener used to handle selecting a component from the recycler view
     */
    private OnComponentClickListener mListener;

    /**
     * An implementation of the OnComponentClickListener interface that allows a user to select a
     * component from the recycler view
     */
    public interface OnComponentClickListener{
        void onComponentClick(int position);
    }

    /**
     * Sets an on click listener for a component in the recycler view
     * @param listener
     */
    public void setOnComponentClickListener(OnComponentClickListener listener)
    {
        mListener = listener;
    }

    /**
     * A class that represents the views that are held within the recycler view
     */
    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;

        public ExampleViewHolder(@NonNull View itemView, OnComponentClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView_search_screen);
            mTextView1 = itemView.findViewById(R.id.username_textView_search_screen);
            mTextView2 = itemView.findViewById(R.id.description_textView_search_screen);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                /**
                 * Used to identify which element in the recycler view that the user has clicked on
                 */
                public void onClick(View view) {
                    if(listener != null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            listener.onComponentClick(position);
                        }
                    }
                }
            });
        }
    }

    /**
     * A constructor used to create a new RecyclerViewAdapter
     * @param exampleList
     *  The list of the components that are used in the recycler view adapter
     */
    public RecyclerViewAdapter(ArrayList<RecyclerViewComponent> exampleList)
    {
        mExampleList = exampleList;
        fullList = new ArrayList<>(exampleList);
    }

    @NonNull
    @Override
    //pass layout of cards to the adapter here
    /**
     * Creates a view for holding the information to be displayed in each component of the recycler
     * view
     */
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_component,
                parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    /**
     * Passes values to local views so that information will be visible on each component of the
     * recycler view
     */
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        RecyclerViewComponent currentItem = mExampleList.get(position);

        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
    }

    @Override
    /**
     * Gets the amount of components in the recycler view
     */
    public int getItemCount() {
        return mExampleList.size();
    }
    @Override
    /**
     * Gets the filter for the recycler view
     */
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        /**
         * Performs the filtering for the recycler view
         */
        protected FilterResults performFiltering(CharSequence constraint) {
            List<RecyclerViewComponent> filteredList = new ArrayList<>();
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
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        /**
         * Updates the recycler view to contain the filtered results
         */
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mExampleList.clear();
            mExampleList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };
}
