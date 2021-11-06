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

public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.ExampleViewHolder> implements Filterable {
    private ArrayList<RecyclerViewComponent> mExampleList;
    private ArrayList<RecyclerViewComponent> fullList;
    private OnComponentClickListener mListener;

    public interface OnComponentClickListener{
        void onComponentClick(int position);
    }

    public void setOnComponentClickListener(OnComponentClickListener listener)
    {
        mListener = listener;
    }

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
    public RecyclerViewAdapter(ArrayList<RecyclerViewComponent> exampleList)
    {
        mExampleList = exampleList;
        fullList = new ArrayList<>(exampleList);
    }

    @NonNull
    @Override
    //pass layout of cards to the adapter here
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_component,
                parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    //pass vaules to local views here
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        RecyclerViewComponent currentItem = mExampleList.get(position);

        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
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
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mExampleList.clear();
            mExampleList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };
}
