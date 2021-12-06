package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.werqoutfrontend.model.User;
import com.example.werqoutfrontend.network.Websocket;
import com.example.werqoutfrontend.utils.RecyclerViewAdapterMessage;
import com.example.werqoutfrontend.utils.RecyclerViewMessage;

import org.java_websocket.client.WebSocketClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class contains all of the logic needed for displaying messages between users. You basically
 * get the message log from the server and then you create a recycler view to display all of the messages
 * in. The screen also allows the user to input a message and send it to another user.
 * @author Colin
 */
public class MessagesScreen extends AppCompatActivity implements Serializable {
    /**
     * The recycler view for which the messages will be displayed in
     */
    private RecyclerView mRecyclerView;
    /**
     * The adapter that will handle functionalities associated with the recycler view
     */
    private RecyclerViewAdapterMessage mAdapter;
    /**
     * The layout manager that will handle how views are displayed within the recycler view
     */
    private RecyclerView.LayoutManager mLayoutManager;

    /**
     * An array list containing messages between users
     */
    private ArrayList<RecyclerViewMessage> messages = new ArrayList<>();

    /**
     * A textview where a user can enter a message they wish to send
     */
    private TextView enterMessage;
    /**
     * When the send icon is clicked it will send the message
     */
    private ImageView sendIcon;
    /**
     * The username of the person that you are messaging
     */
    private TextView title;
    /**
     * The websocket client of the current user, used to send messages to other users
     */
    private WebSocketClient cc;
    /**
     * The username of the other user who is being messaged to
     */
    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_screen);
        enterMessage = findViewById(R.id.edit_message_text_message_sceen);
        sendIcon = findViewById(R.id.send_button_messages_screen);
        title = findViewById(R.id.title_message_screen);
        title.setText(getIntent().getSerializableExtra("username").toString());

        username = getIntent().getSerializableExtra("username").toString();
        cc = Websocket.getCc();
        int otherID = (int) getIntent().getSerializableExtra("id");
        ArrayList<String[]> messagesLog = Websocket.getMessageLog(otherID);
        createList(messagesLog);
        buildRecyclerView();

        sendIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Send the message in the input text box when clicked
             */
            public void onClick(View view) {
                String textMessage = enterMessage.getText().toString();
                if(!textMessage.equals(""))
                {
                    addMessage(textMessage, User.currentUser.getId());
                    String sendTextMessage = "@" + Integer.toString(otherID) + ":" +
                            enterMessage.getText().toString();
                    enterMessage.setText("");
                    try {
                        cc.send(sendTextMessage);
                        Websocket.addMessage(otherID, textMessage);
                    }
                    catch(Exception e){
                        Log.d("ExceptionSendMessage:", e.getMessage().toString());
                    }

                }
            }
        });

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(Websocket.newMessage)
                {
                    String [] idAndMessage = Websocket.getRecievedMessage();
                    try{
                        String messageText = idAndMessage[1];
                        int id = Integer.parseInt(idAndMessage[0]);
                        addMessage(messageText, id);
                    }
                    catch (NumberFormatException e)
                    {

                    }
                }
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    private void createList(ArrayList<String[]> m)
    {
        if(m == null)
        {
            return;
        }
        for(String [] textMessage: m)
        {
            try {
                String tM = textMessage[0];
                int id = Integer.parseInt(textMessage[1]);
                RecyclerViewMessage messageComponent;
                if (id == User.currentUser.getId()) {
                    messageComponent = new RecyclerViewMessage(tM, User.currentUser.getUsername());
                } else {
                    messageComponent = new RecyclerViewMessage(tM, username);
                }
                messages.add(messageComponent);
            }
            catch (NumberFormatException e)
            {
                Log.d("onlineNotification", "Crisis averted");
            }
        }
    }

    private void buildRecyclerView()
    {
        mRecyclerView = findViewById(R.id.message_view_message_screen);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerViewAdapterMessage(messages);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
    private void addMessage(String textMessage, int id) {

        if (id == User.currentUser.getId()) {
            messages.add(new RecyclerViewMessage(textMessage, User.currentUser.getUsername()));
            mAdapter.notifyItemInserted(messages.size());
        } else {
            messages.add(new RecyclerViewMessage(textMessage, username));
            mAdapter.notifyItemInserted(messages.size());
        }
    }
}