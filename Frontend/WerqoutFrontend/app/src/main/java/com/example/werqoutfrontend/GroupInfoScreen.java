package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.werqoutfrontend.model.Athlete;
import com.example.werqoutfrontend.model.Team;
import com.example.werqoutfrontend.model.Workout;
import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * The GroupInfoScreen class gives functionality to the group_info_screen.xml screen. This screen
 * includes a Scroll View containing the team's members and upcoming workouts. It also gives the
 * Coach the option to go to the Add/Delete workout screen or the Edit Group screen.
 * @author Angelo Bellizzi
 */
public class GroupInfoScreen extends AppCompatActivity {

    private String TAG = ServerRequest.class.getSimpleName();
    private String tag_json_obj_post = "jobj_req_post";

    /**
     * The selected team.
     */
    public Team selectedGroup;

    /**
     * A JSONObject of the team selected in CoachGroupsScreen.
     */
    private JSONObject team;

    /**
     * The ID of the selected team.
     */
    private int teamId;

    /**
     * This screen's context.
     */
    private Context context = this;

    /**
     * The linear layout within the Scroll View in group_info_screen.xml
     */
    private LinearLayout linearScroll;

    /**
     * Params for the text in the Scroll View.
     */
    private ViewGroup.LayoutParams params;

    /**
     * Overrides the onCreate function. Gives the interactive buttons and texts functionality.
     * Connects this class to group_info_screen.xml
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_info_screen);

        TextView groupNameLabel = findViewById(R.id.group_name_label_group_info);
        Button backButton = findViewById(R.id.back_button_group_info);
        Button addDeleteButton = findViewById(R.id.addDeleteWorkouts_button_group_info);
        Button editGroupButton = findViewById(R.id.editGroup_button_group_info);
        groupNameLabel.setText(CoachGroupsScreen.getSelectedGroup());

        backButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function directs the user to the Coach Groups Screen when the
             * "back" button is clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), CoachGroupsScreen.class));
            }
        });
        addDeleteButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function directs the user to the Add/Delete Workout Screen when the
             * "add/delete workout" button is clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), AddDeleteWorkoutScreen.class));
            }
        });
        editGroupButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function directs the user to the Edit Group Screen when the "edit group"
             * button is clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), EditGroupScreen.class));
            }
        });

        linearScroll = (LinearLayout)findViewById(R.id.scrollLinear_group_info);

        TextView membersText = new TextView(context);
        membersText.setText("Members:");
        linearScroll.addView(membersText);
        params = membersText.getLayoutParams();
        CoachHomeScreen.setTextSettings(params, membersText);
        team = CoachGroupsScreen.getTeamInfo();
        try {
            teamId = ((int)team.get("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Const.CURRENT_URL = "http://coms-309-034.cs.iastate.edu:8080/teams/" + teamId + "/athletes";
        ServerRequest displayMembers = new ServerRequest();
        displayMembers.jsonArrayRequest(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
            }
            @Override
            public void onSuccess(JSONArray users) {
                try {
                    for(int i = 0; i < users.length(); i++)
                    {
                        JSONObject user = users.getJSONObject(i);
                        TextView membersText = new TextView(context);
                        membersText.setText(user.get("userName").toString());
                        linearScroll.addView(membersText);
                        ViewGroup.LayoutParams params = membersText.getLayoutParams();
                        CoachHomeScreen.setTextSettings(params, membersText);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, Const.CURRENT_URL);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                displayWorkouts();
            }
        }, 300);
    }

    /**
     * Displays the workouts in the Scroll View. Called in a delay.
     */
    private void displayWorkouts()
    {
        TextView workoutText = new TextView(context);
        workoutText.setText("\nWorkouts:");
        linearScroll.addView(workoutText);
        params = workoutText.getLayoutParams();
        CoachHomeScreen.setTextSettings(params, workoutText);

        Const.CURRENT_URL = "http://coms-309-034.cs.iastate.edu:8080/events/team/" + teamId + "/events";
        ServerRequest allWorkouts = new ServerRequest();
        allWorkouts.jsonArrayRequest(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
            }
            @Override
            public void onSuccess(JSONArray users) {
                for(int i = 0; i < users.length(); i++)
                {
                    try {
                        JSONObject workout = users.getJSONObject(i);
                        TextView textWorkout = new TextView(context);
                        String desc = workout.get("description").toString();
                        String dateAndTime = AddDeleteWorkoutScreen.formatDateTimeFromDatabase(workout.get("date").toString());
                        textWorkout.setText(desc + " " + dateAndTime);
                        linearScroll.addView(textWorkout);
                        ViewGroup.LayoutParams params = textWorkout.getLayoutParams();
                        CoachHomeScreen.setTextSettings(params, textWorkout);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, Const.CURRENT_URL);
    }

    /**
     * Gets the team's workouts from the database.
     * @param t
     * the team that you want to get the workouts for
     * @return
     * returns an ArrayList of the team's workouts
     */
    public ArrayList<Workout> getWorkouts(Team t)
    {
        //gets workouts from database
        return null;
    }

    /**
     * Gets the team's athletes from the database.
     * @param t
     * the team that you want to get the athletes for
     * @return
     * returns an ArrayList of the team's athletes
     */
    public ArrayList<Athlete> getAthletes(Team t)
    {
        //gets athletes from database
        return null;
    }
}