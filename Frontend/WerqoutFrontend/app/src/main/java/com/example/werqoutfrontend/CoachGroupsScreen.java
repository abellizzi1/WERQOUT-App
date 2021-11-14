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

import java.util.HashMap;
import java.util.Map;

/**
 * The CoachGroupsScreen class gives functionality to the coach_groups_screen.xml screen. It includes a
 * Scroll View of the Coach's teams and allows the Coach to select one of these teams. Also, the Coach
 * can create a new Team on this screen.
 * @author Angelo Bellizzi
 */
public class CoachGroupsScreen extends AppCompatActivity implements View.OnClickListener {

    /**
     * Stores the name of the selected group.
     */
    private static String selectedGroup = "";

    /**
     * The linear layout within the Scroll View in coach_groups_screen.xml
     */
    private LinearLayout linearScroll;

    /**
     * Overrides the onCreate function. Gives the interactive buttons and texts functionality.
     * Connects this class to coach_groups_screen.xml
     * @param savedInstanceState
     */

    private JSONObject newTeam;
    private boolean test = false;

    private static JSONObject selectedTeamInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coach_groups_screen);

        Context context = this;

        Button backButton = findViewById(R.id.back_button_groups);

        backButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function directs the user to the Coach Home Screen when the "back"
             * button is clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), CoachHomeScreen.class));
            }
        });

        linearScroll = (LinearLayout)findViewById(R.id.scrollLinear_groups);
        Const.CURRENT_URL = "http://coms-309-034.cs.iastate.edu:8080/coaches/" + LoginScreen.getId() + "/teams";

        ServerRequest displayAllGroups = new ServerRequest();
        displayAllGroups.jsonGetRequest(new VolleyCallback() {
            int bId = 0;
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    selectedTeamInfo = result;
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
                    groupButton.setId(bId);
                    bId++;
                    groupButton.setOnClickListener(CoachGroupsScreen.this);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(JSONArray result) {

            }
        }, Const.CURRENT_URL);

        Button createGroupButton = findViewById(R.id.create_button_groups);
        createGroupButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function posts a new team to the database and puts the team in the
             * current coach's path when the "create" button is clicked.
             * @param view
             */
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
                request = new ServerRequest();
                request.jsonGetRequest(new VolleyCallback() {
                    @Override
                    public void onSuccess(JSONObject result) {
                    }

                    @Override
                    public void onSuccess(JSONArray result) {
                        try {
                            newTeam = result.getJSONObject(result.length()-1);
                            test = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, Const.CURRENT_URL);
                Const.CURRENT_URL = "http://coms-309-034.cs.iastate.edu:8080/coaches/" + LoginScreen.getId() + "/teams";
                request.jsonObjectRequest(Const.CURRENT_URL,2, newTeam);
                startActivity(new Intent(view.getContext(), CoachGroupsScreen.class));
            }
        });
    }

    /**
     * This onClick function directs the user to the Group Info Screen and displays the information
     * for the group that was clicked on.
     * @param v
     */
    @Override
    public void onClick(View v) {
        selectedGroup = ((Button)linearScroll.findViewById(v.getId())).getText().toString();
        startActivity(new Intent(v.getContext(), GroupInfoScreen.class));
    }

    /**
     * Gets the selected group that was clicked on.
     * @return
     * returns the selected group
     */
    public static String getSelectedGroup()
    {
        return selectedGroup;
    }

    public static JSONObject getTeamInfo()
    {
        return selectedTeamInfo;
    }
}