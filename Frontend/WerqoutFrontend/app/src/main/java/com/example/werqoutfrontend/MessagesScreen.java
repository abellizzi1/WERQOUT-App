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
import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.RecyclerViewAdapterMessage;
import com.example.werqoutfrontend.utils.RecyclerViewMessage;
import com.example.werqoutfrontend.utils.VolleyCallback;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

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
     * The websocket client for this user
     */
    private Button back;
    private WebSocketClient cc;
    private static boolean closed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_screen);
        enterMessage = findViewById(R.id.edit_message_text_message_sceen);
        sendIcon = findViewById(R.id.send_button_messages_screen);
        title = findViewById(R.id.title_message_screen);
        back = findViewById(R.id.close_cc_button_messages_screen);
        title.setText(getIntent().getSerializableExtra("username").toString());

        Draft[] drafts = { new Draft_6455() };
        //TODO:Update this url to match the server path
        String w = "ws://10.29.159.69:8080/message/1";
        try{
            Log.d("Socket:", "Trying socket");
            cc = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    Log.d("OPEN", "run() returned: " + "is connecting");
                    closed = false;
                }

                @Override
                public void onMessage(String message) {
                    Log.d("", "run() returned: " + message);
                    Scanner scan = new Scanner(message);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            while(scan.hasNextLine()) {
                                addMessage(scan.nextLine());
                            }
                        }
                    });
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("CLOSE", "onClose() returned: " + reason);
                }

                @Override
                public void onError(Exception e) {
                    Log.d("Exception:", e.toString());
                    closed = true;
                }
            };
        }
        catch (URISyntaxException e)
        {
            Log.d("Exception:", e.getMessage().toString());
            e.printStackTrace();
        }
        cc.connect();
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
                    //Add the onMessage implementation in here
                    enterMessage.setText("");
                    try {
                        cc.send(textMessage);
                    }
                    catch(Exception e){
                        Log.d("ExceptionSendMessage:", e.getMessage().toString());
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cc.close();
                startActivity(new Intent(getApplicationContext(), SelectMessageScreen.class));
            }
        });
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
    private void addMessage(String textMessage){
        messages.add(new RecyclerViewMessage(textMessage, User.currentUser.getUsername()));
        mAdapter.notifyItemInserted(messages.size());
    }
}