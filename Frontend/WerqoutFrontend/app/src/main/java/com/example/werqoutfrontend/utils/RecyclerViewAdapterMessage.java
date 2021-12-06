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

/**
 * This class provides functionality to the recycler view that is used on the messages screen.
 * All that it really does is format the messages within the recycler view and manage the entry
 * of new messages.
 * @Author Colin
 */

public class RecyclerViewAdapterMessage extends RecyclerView.Adapter <RecyclerViewAdapterMessage.ExampleViewHolder> {
    private ArrayList<RecyclerViewMessage> messageList;

    /**
     * Represents the information that will be displayed in each component of the messages
     * recycler view. mTextView1 will contain the message, the username textview will display
     * who sent the message, and color sidebar helps differentiate who sent the messages.
     */
    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView1;
        public TextView username;
        public View colorSidebar;

        /**
         * A constructor for the view holder that will display all of the information associated
         * with a message, being who sent it and the message itself
         * @param itemView
         *  The item view that will be displayed in the recycler view
         */
        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.message_text_message_screen);
            username = itemView.findViewById(R.id.username_text_message_screen);
            colorSidebar = itemView.findViewById(R.id.sidebar_color);
        }
    }

    /**
     * Constructs a recycler view adapter for the messaging recycler view
     * @param list
     *  The list of messages to be displayed
     */
    public RecyclerViewAdapterMessage(ArrayList<RecyclerViewMessage> list)
    {
        messageList = list;
    }

    @NonNull
    @Override
    /**
     * This method passes the layout of the cards that will display the messages within the recycler
     * view to the adapter
     */
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_message,
                parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    //pass vaules to local views here
    /**
     * This method receives the values of the local views and makes a few adjustments
     * before making displaying each message component in the recycler view.
     */
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        RecyclerViewMessage currentItem = messageList.get(position);
        holder.mTextView1.setText(currentItem.getMessage());
        holder.username.setText(currentItem.getUsername());
//        if(currentItem.getUsername().equalsIgnoreCase(User.currentUser.getUsername()))
        if(currentItem.getUsername().equalsIgnoreCase(User.currentUser.getUsername()))
        {
            holder.username.setTextColor(Color.BLUE);
           // holder.colorSidebar.setBackgroundColor(Color.BLUE);
        }
        else
        {
            holder.username.setTextColor(Color.MAGENTA);
            holder.colorSidebar.setBackgroundColor(Color.MAGENTA);
        }
    }

    @Override
    /**
     * Returns the size of the message list
     */
    public int getItemCount() {
        return messageList.size();
    }
}

