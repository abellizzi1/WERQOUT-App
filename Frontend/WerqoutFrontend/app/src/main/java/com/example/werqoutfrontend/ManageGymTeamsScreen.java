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

import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ManageGymTeamsScreen extends AppCompatActivity implements View.OnClickListener {

    /*
    all teams listed. click on team to view info. able to delete teams.
     */

    /**
     * The linear layout within the Scroll View in manage_gym_teams_screen.xml
     */
    private LinearLayout linearScroll;

    /**
     * An ArrayList containing all teams in the database.
     */
    private ArrayList<JSONObject> listTeams;

    /**
     * Stores the name of the selected group.
     */
    private static String selectedGroup = "";

    /**
     * The information of the selected team.
     */
    private static JSONObject selectedTeamInfo;

    /**
     * Overrides the onCreate function. Gives the interactive buttons and texts functionality.
     * Connects this class to manage_gym_teams_screen.xml
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_gym_teams_screen);
        Context context = this;
        linearScroll = (LinearLayout)findViewById(R.id.scrollLinear_manageGymTeams);

        Button backButton = findViewById(R.id.back_button_manageGymTeams);
        backButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function directs the user to the Gym Owner Home Screen when the "back"
             * button is clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), GymOwnerHomeScreen.class));
            }
        });

        Const.CURRENT_URL = Const.URL_JSON_REQUEST_TEAMS;
        ServerRequest displayAllTeams = new ServerRequest();
        displayAllTeams.jsonArrayRequest(new VolleyCallback() {
            int bId = 0;
            @Override
            public void onSuccess(JSONObject result) {
            }
            @Override
            public void onSuccess(JSONArray result) {
                for (int i = 0; i < result.length(); i++)
                {
                    try {
                        JSONObject currResult = result.getJSONObject(i);
                        listTeams.add(currResult);
                        Button groupButton = new Button (context);
                        groupButton.setText(currResult.get("name").toString());
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
                        groupButton.setOnClickListener(ManageGymTeamsScreen.this);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, Const.CURRENT_URL);
    }

    /**
     * This onClick function directs the user to the Team Info Screen and displays the information
     * for the group that was clicked on.
     * @param v
     */
    @Override
    public void onClick(View v) {
        selectedGroup = ((Button)linearScroll.findViewById(v.getId())).getText().toString();
        selectedTeamInfo = listTeams.get(v.getId());
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

    /**
     * Gets the information of the selected team.
     * @return
     *  returns a JSONObject of the selected team
     */
    public static JSONObject getTeamInfo()
    {
        return selectedTeamInfo;
    }
}