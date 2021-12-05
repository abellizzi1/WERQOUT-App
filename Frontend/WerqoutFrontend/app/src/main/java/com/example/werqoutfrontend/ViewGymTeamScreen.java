package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewGymTeamScreen extends AppCompatActivity {

    /**
     * The linear layout within the Scroll View in view_gym_team_screen.xml
     */
    private LinearLayout linearScroll;

    /**
     * The context of this screen.
     */
    private Context context = this;

    /**
     * JSONObject of the team selected on the previous page.
     */
    private JSONObject team;

    /**
     * The ID of the selected team.
     */
    private int teamId;

    /**
     * Params for the text in the Scroll View.
     */
    private ViewGroup.LayoutParams params;

    /**
     * Overrides the onCreate function. Gives the interactive buttons and texts functionality.
     * Connects this class to view_gym_team_screen.xml
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_gym_team_screen);
        linearScroll = (LinearLayout)findViewById(R.id.scrollLinear_viewGymTeam);
        TextView teamLabel = findViewById(R.id.team_label_viewGymTeam);
        teamLabel.setText(ManageGymTeamsScreen.getSelectedGroup());
        team = ManageGymTeamsScreen.getTeamInfo();
        try {
            teamId = ((int)team.get("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Button backButton = findViewById(R.id.back_button_viewGymTeam);
        backButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function directs the user to the Manage Gym Teams Screen when the "back"
             * button is clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ManageGymTeamsScreen.class));
            }
        });

        TextView membersText = new TextView(context);
        membersText.setText("Members:");
        linearScroll.addView(membersText);
        params = membersText.getLayoutParams();
        CoachHomeScreen.setTextSettings(params, membersText);
        team = ManageGymTeamsScreen.getTeamInfo();
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
}