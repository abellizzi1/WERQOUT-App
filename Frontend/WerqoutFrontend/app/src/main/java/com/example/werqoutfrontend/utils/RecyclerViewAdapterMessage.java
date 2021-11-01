package com.example.werqoutfrontend.utils;

import android.graphics.Color;
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
import com.example.werqoutfrontend.model.User;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterMessage extends RecyclerView.Adapter <RecyclerViewAdapterMessage.ExampleViewHolder> {
    private ArrayList<RecyclerViewMessage> messageList;
    private OnComponentClickListener mListener;
    private boolean sender;

    public interface OnComponentClickListener{
        void onComponentClick(int position);
    }

    public void setOnComponentClickListener(OnComponentClickListener listener)
    {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView1;
        public TextView username;

        public ExampleViewHolder(@NonNull View itemView, OnComponentClickListener listener) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.message_text_message_screen);
            username = itemView.findViewById(R.id.username_text_message_screen);
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
    public RecyclerViewAdapterMessage(ArrayList<RecyclerViewMessage> list)
    {
        messageList = list;
    }

    @NonNull
    @Override
    //pass layout of cards to the adapter here
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_message,
                parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    //pass vaules to local views here
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        RecyclerViewMessage currentItem = messageList.get(position);

//        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getMessage());
        holder.username.setText(currentItem.getUsername());
//        if(currentItem.getUsername().equalsIgnoreCase(User.currentUser.getUsername()))
        if(currentItem.getUsername().equalsIgnoreCase("colin"))
        {
            holder.username.setTextColor(Color.BLUE);
        }
        else
        {
            holder.username.setTextColor(Color.MAGENTA);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}

