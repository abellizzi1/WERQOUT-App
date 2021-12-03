package com.example.werqoutfrontend.network;
import android.util.Log;

import com.example.werqoutfrontend.model.User;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;



public class Websocket {
    private static boolean closed;
    private static WebSocketClient cc;
    public Websocket(){
        Draft[] drafts = { new Draft_6455() };
        String w = "ws://10.29.159.69:8080/message/" + Integer.toString(User.currentUser.getId());
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
    }

    public WebSocketClient getCc()
    {
        return cc;
    }
}
