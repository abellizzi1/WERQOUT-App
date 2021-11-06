package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CoachGroupsScreen extends AppCompatActivity implements View.OnClickListener {

    private static String selectedGroup = "";
    private LinearLayout linearScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coach_groups_screen);

        Context context = this;

        Button backButton = findViewById(R.id.back_button_groups);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), CoachHomeScreen.class));
            }
        });

        linearScroll = (LinearLayout)findViewById(R.id.scrollLinear_groups);

        Const.CURRENT_URL = "http://coms-309-034.cs.iastate.edu:8080/coaches/1/teams";
        //Const.CURRENT_URL = "http://coms-309-034.cs.iastate.edu:8080/teams/1/athletes";
        ServerRequest displayAllGroups = new ServerRequest();
        displayAllGroups.jsonArrayRequest(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                //                for(int i = 0; i < users.length(); i++)
//                {
                try {
                    Button groupButton = new Button (context);
                    groupButton.setText(result.get("name").toString());
                    linearScroll.addView(groupButton);
                    ViewGroup.LayoutParams params;
                    params = groupButton.getLayoutParams();
                    params.height = 300;
                    params.width = 1409;
                    groupButton.setLayoutParams(params);
                    groupButton.setTextSize(30);
                    groupButton.setTextColor(Color.parseColor("#000000"));
                    groupButton.setBackgroundColor(Color.parseColor("#00FFA7"));
                    groupButton.setOnClickListener((View.OnClickListener) context);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // }
            }

            @Override
            public void onSuccess(JSONArray result) {
//                for (int i = 0; i < result.length(); i++)
//                {
//                    try {
//                        JSONObject user = result.getJSONObject(i);
//                        Button groupButton = new Button (context);
//                        groupButton.setText(user.get("name").toString());
//                        linearScroll.addView(groupButton);
//                        ViewGroup.LayoutParams params;
//                        params = groupButton.getLayoutParams();
//                        params.height = 300;
//                        params.width = 1409;
//                        groupButton.setLayoutParams(params);
//                        groupButton.setTextSize(30);
//                        groupButton.setTextColor(Color.parseColor("#000000"));
//                        groupButton.setBackgroundColor(Color.parseColor("#00FFA7"));
//                        groupButton.setOnClickListener((View.OnClickListener) context);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
            }
        },Const.CURRENT_URL);






        Button groupButton;
        ViewGroup.LayoutParams params;
//        for (int i = 0; i < 10; i++)
//        {
//            groupButton = new Button(context);
//            groupButton.setText("Group " + (i+1));
//            linearScroll.addView(groupButton);
//            params = groupButton.getLayoutParams();
//            params.height = 300;
//            params.width = 1409;
//            groupButton.setLayoutParams(params);
//            groupButton.setTextSize(30);
//            groupButton.setTextColor(Color.parseColor("#000000"));
//            groupButton.setBackgroundColor(Color.parseColor("#00FFA7"));
//            groupButton.setId(i);
//            groupButton.setOnClickListener(this);
//        }

        Button createGroupButton = findViewById(R.id.create_button_groups);
        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText newGroupName = findViewById(R.id.groupName_input_groups);
                String newGroup = newGroupName.getText().toString();
                Map params = new HashMap<>();
                params.put("name", newGroup);
                params.put("description", "Coach id: " + LoginScreen.getId());
                Const.CURRENT_URL = Const.URL_JSON_REQUEST_TEAMS; // http://coms-309-034.cs.iastate.edu:8080/teams
                ServerRequest request = new ServerRequest();
                request.jsonObjectRequest(Const.CURRENT_URL,1, new JSONObject(params));
                Const.CURRENT_URL = "http://coms-309-034.cs.iastate.edu:8080/coaches/" + LoginScreen.getId() + "/teams";
                request.jsonObjectRequest(Const.CURRENT_URL,2, new JSONObject(params));
                startActivity(new Intent(view.getContext(), CoachGroupsScreen.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        selectedGroup = ((Button)linearScroll.findViewById(v.getId())).getText().toString();
        startActivity(new Intent(v.getContext(), GroupInfoScreen.class));
    }

    public static String getSelectedGroup()
    {
        return selectedGroup;
    }
}