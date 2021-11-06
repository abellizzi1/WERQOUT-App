package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.werqoutfrontend.model.Athlete;
import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditGroupScreen extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout linearScroll;
    private static String selectedMember = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_group_screen);

        Button backButton = findViewById(R.id.back_button_edit_group);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), GroupInfoScreen.class));
            }
        });

        Context context = this;

        linearScroll = (LinearLayout)findViewById(R.id.scrollLinear_edit_group);

        Const.CURRENT_URL = "http://coms-309-034.cs.iastate.edu:8080/teams/1/athletes";
        ServerRequest displayAllGroups = new ServerRequest();
        displayAllGroups.jsonArrayRequest(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
            }

            @Override
            public void onSuccess(JSONArray users) {
                for(int i = 0; i < users.length(); i++)
                {
                    try {
                        JSONObject user = users.getJSONObject(i);
                        Button groupButton = new Button (context);
                        groupButton.setText(user.get("userName").toString());
                        linearScroll.addView(groupButton);
                        ViewGroup.LayoutParams params;
                        params = groupButton.getLayoutParams();
                        params.height = 200;
                        params.width = 1409;
                        groupButton.setLayoutParams(params);
                        groupButton.setTextSize(30);
                        groupButton.setTextColor(Color.parseColor("#000000"));
                        groupButton.setBackgroundColor(Color.parseColor("#00FFA7"));
                        groupButton.setId(i);
                        groupButton.setOnClickListener((View.OnClickListener) context);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        },Const.CURRENT_URL);

        Button addAthleteButton = findViewById(R.id.add_button_edit_group);
        addAthleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map params = new HashMap<>();
                params.put("userName", "test1");
                params.put("lastName", "test1lastname");
                params.put("email", "test1@iastate.edu");
                params.put("password", "Testtest1");
                Const.CURRENT_URL = "http://coms-309-034.cs.iastate.edu:8080/teams/1/athletes";
                ServerRequest request = new ServerRequest();
                request.jsonObjectRequest(Const.CURRENT_URL,2, new JSONObject(params));
                startActivity(new Intent(view.getContext(), EditGroupScreen.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        selectedMember = ((Button)linearScroll.findViewById(v.getId())).getText().toString();
        TextView selected = findViewById(R.id.selected_label_edit_group);
        selected.setText("Selected: " + selectedMember);
    }

}