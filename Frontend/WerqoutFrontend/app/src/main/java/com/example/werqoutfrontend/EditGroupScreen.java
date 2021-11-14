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

/**
 * The EditGroupScreen class gives functionality to the edit_group_screen.xml screen. It includes a
 * scroll view that includes all of the Team's members, and these members can be selected and deleted
 * from the team. Also, the Team name can be edited and a group member can be added to the Team from
 * this screen.
 * @author Angelo Bellizzi
 */
public class EditGroupScreen extends AppCompatActivity implements View.OnClickListener{

    /**
     * The linear layout within the Scroll View that contains the Team's members.
     */
    private LinearLayout linearScroll;

    /**
     * A string that contains the selected member from the Scroll View.
     */
    private static String selectedMemberString = "";

    private JSONObject selectedMemberJson;

    private JSONObject team;

    private ArrayList<JSONObject> jsonMembersArray;

    /**
     * Overrides the onCreate function. Gives the interactive buttons and texts functionality.
     * Connects this class to edit_group_screen.xml
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_group_screen);
        team = CoachGroupsScreen.getTeamInfo();
        jsonMembersArray = new ArrayList<JSONObject>();

        Button backButton = findViewById(R.id.back_button_edit_group);
        backButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function directs the user to the Group Info Screen when the "back"
             * button is clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), GroupInfoScreen.class));
            }
        });

        Context context = this;

        linearScroll = (LinearLayout)findViewById(R.id.scrollLinear_edit_group);

        try {
            Const.CURRENT_URL = "http://coms-309-034.cs.iastate.edu:8080/teams/" + team.get("id") + "/athletes";
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                        jsonMembersArray.add(users.getJSONObject(i));
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
        EditText addAthleteEmail = findViewById(R.id.athleteEmail_input_edit_group);
        addAthleteButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function puts an athlete in the current Team when the "add" button
             * is clicked. The athlete that is put into the team is found when the Coach enters
             * the athlete's email.
             * @param view
             */
            @Override
            public void onClick(View view) {
                ServerRequest addAthleteRequest = new ServerRequest();
                addAthleteRequest.jsonArrayRequest(new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {
                    }

                    @Override
                    public void onSuccess(JSONArray users) {
                        for(int i = 0; i < users.length(); i++)
                        {
                            try {
                                JSONObject user = users.getJSONObject(i);
                                if(user.get("email").toString().equals(addAthleteEmail.getText().toString()))
                                {
                                    String athleteUrl = Const.URL_JSON_REQUEST_ATHLETES + "/" + user.get("id") + "/teams";
                                    ServerRequest postTeam = new ServerRequest();
                                    postTeam.jsonObjectRequest(athleteUrl, 1, team);
                                    break;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },Const.CURRENT_URL);
            }
        });

        Button editNameButton = findViewById(R.id.submit_button_edit_group);
        EditText editNameText = findViewById(R.id.newGroupName_input_edit_group);
        editNameButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function changes the name of the team when the "submit" button is clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {
                ServerRequest changeNameRequest = new ServerRequest();
                try {
                    Const.CURRENT_URL = Const.URL_JSON_REQUEST_TEAMS + "/" + team.get("id");
                    team.put("name", editNameText.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                changeNameRequest.jsonObjectRequest(Const.CURRENT_URL, 2, team);
        }
    });

        Button removeAthleteButton = findViewById(R.id.remove_button_edit_group);
        removeAthleteButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function removes an athlete from the team when the "remove" button is clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {
                String athleteUrl = "";
                try {
                    athleteUrl = Const.URL_JSON_REQUEST_ATHLETES + "/" + selectedMemberJson.get("id") + "/teams";
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ServerRequest removeAthleteRequest = new ServerRequest();
                removeAthleteRequest.jsonObjectRequest(athleteUrl, 3, team);
            }
        });
    }

    /**
     * This onClick function keeps track of the selected member. Once a member is selected,
     * they can be deleted from the team.
     * @param v
     */
    @Override
    public void onClick(View v) {
        selectedMemberString = ((Button)linearScroll.findViewById(v.getId())).getText().toString();
        TextView selected = findViewById(R.id.selected_label_edit_group);
        selected.setText("Selected: " + selectedMemberString);
        selectedMemberJson = jsonMembersArray.get(v.getId());
    }

}