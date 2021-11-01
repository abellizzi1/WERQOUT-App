package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.werqoutfrontend.utils.RecyclerViewAdapterMessage;
import com.example.werqoutfrontend.utils.RecyclerViewMessage;

import java.util.ArrayList;

public class MessagesScreen extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapterMessage mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<RecyclerViewMessage> messages = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_screen);
        RecyclerViewMessage test1 = new RecyclerViewMessage("hey bozo this " +
                "sentence is supposed to be long to test the limits of the display","colin");
        RecyclerViewMessage test2 = new RecyclerViewMessage("okay bro","joe");
        messages.add(test1);
        messages.add(test2);
        mRecyclerView = findViewById(R.id.message_view_message_screen);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerViewAdapterMessage(messages);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


    }
}