package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SelectMessageScreen extends AppCompatActivity {
    private JSONArray messages;
    private TableLayout table;
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_message_screen);
        linearLayout =(LinearLayout) findViewById(R.id.linear_layout_select_message_screen);

        ServerRequest serverRequest = new ServerRequest();
        serverRequest.jsonArrayRequest(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {

            }

            @Override
            public void onSuccess(JSONArray result) {
                messages = result;
                updateTable();
            }
        },Const.POSTMAN_TEST_URL);
    }
    private void updateTable()
    {
        for(int i = 0; i < messages.length(); i++) {
            try {
                TableRow tableRow = new TableRow(this);
                tableRow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT
                        ,LayoutParams.WRAP_CONTENT, 30));
                TextView username = new TextView(this);

                String userName = messages.getJSONObject(i).getString("userName");
                username.setText(userName);
                username.setTextColor(Color.WHITE);
                username.setTextSize(40);
                username.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                tableRow.addView(username);

                tableRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), MessagesScreen.class);
                        intent.putExtra("username", userName);
                        startActivity(intent);
                    }
                });
                tableRow.setBackgroundResource(R.drawable.cell_shape);
                linearLayout.addView(tableRow, new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}