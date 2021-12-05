package com.example.werqoutfrontend.network;
import android.util.Log;

import com.example.werqoutfrontend.MessagesScreen;
import com.example.werqoutfrontend.model.User;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.VolleyCallback;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;



public class Websocket {
    private static boolean closed;
    public static boolean newMessage;
    private static WebSocketClient cc;
    private ArrayList<String[]> messageLog;
    private static HashMap <Integer, ArrayList<String[]>> messageMap = new HashMap<>();
    private static String recievedMessage = "no recent message";

    public  Websocket(){
        Draft[] drafts = { new Draft_6455() };
        String w = "ws://coms-309-034.cs.iastate.edu:8080/message/" + Integer.toString(User.currentUser.getId());
        try{
            Log.d("Socket:", "Trying socket");
            cc = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    Log.d("OPEN", "run() returned: " + "is connecting");
                    closed = false;
                    String url = Const.URL_JSON_REQUEST_ATHLETES +"/" +User.currentUser.getId() + "/dms";
                    ServerRequest requestDMS = new ServerRequest();
                    requestDMS.jsonArrayRequest(new VolleyCallback() {
                        @Override
                        public void onSuccess(JSONObject result) {

                        }

                        @Override
                        public void onSuccess(JSONArray result) {
                            for(int i = 0; i < result.length(); i++)
                            {
                                messageLog = new ArrayList<>();
                                try {
                                    JSONObject dmObject = result.getJSONObject(i);
                                    int idFrom;
                                    if(dmObject.getJSONObject("athlete1").getInt("id") !=
                                    User.currentUser.getId())
                                    {
                                        idFrom = dmObject.getJSONObject("athlete1").getInt("id");
                                    }
                                    else
                                    {
                                        idFrom = dmObject.getJSONObject("athlete2").getInt("id");
                                    }
                                    JSONArray messages = dmObject.getJSONArray("messages");
                                    for(int j = 0; j < messages.length(); j++)
                                    {
                                        JSONObject message = messages.getJSONObject(j);
                                        String messageString = message.getString("data");
                                        String sender = message.getJSONObject("from").getString("id");
                                        String [] messageAndSender = {messageString, sender};
                                        messageLog.add(messageAndSender);
                                    }
                                    messageMap.put(idFrom, messageLog);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }, url);
                }

                @Override
                public void onMessage(String message) {

                    Log.d("", "run() returned: " + message);

                    newMessage = true;
                    recievedMessage = message;
                    String [] temp = message.split(":", 2);
                    String [] mAndS = {temp[1], temp[0]};
                    messageLog.add(mAndS);
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

    public static WebSocketClient getCc()
    {
        return cc;
    }

    public static ArrayList<String[]> getMessageLog(int otherID)
    {
        return messageMap.get(otherID);
    }
    public static String[] getRecievedMessage(){
        newMessage = false;
        String [] message = recievedMessage.split(":",2);
        recievedMessage = "no new message";
        return message;
    }
}
