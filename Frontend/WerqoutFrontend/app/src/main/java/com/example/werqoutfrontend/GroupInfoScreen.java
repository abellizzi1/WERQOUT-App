package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

        LinearLayout linearScroll = (LinearLayout)findViewById(R.id.scrollLinear_group_info);
        ViewGroup.LayoutParams params;
        Context context = this;

        TextView membersText = new TextView(context);
        membersText.setText("Members:");
        linearScroll.addView(membersText);
        params = membersText.getLayoutParams();
        CoachHomeScreen.setTextSettings(params, membersText);
        JSONObject team = CoachGroupsScreen.getTeamInfo();

        try {
            Const.CURRENT_URL = "http://coms-309-034.cs.iastate.edu:8080/teams/" + team.get("id") + "/athletes";
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
//
//        for (int i = 0; i < 4; i++)
//        {
//            membersText = new TextView(this);
//            membersText.setText("John Doe");
//            linearScroll.addView(membersText);
//            params = membersText.getLayoutParams();
//            CoachHomeScreen.setTextSettings(params, membersText);
//            if (i == 3)
//            {
//                membersText.setText("John Doe\n");
//            }
//        }
//
//        TextView liftText = new TextView(this);
//        TextView dateText;
//        TextView timeText;
//        liftText.setText("Workouts:");
//        linearScroll.addView(liftText);
//        params = liftText.getLayoutParams();
//        CoachHomeScreen.setTextSettings(params, liftText);
//        for (int i = 0; i < 10; i++)
//        {
//            liftText = new TextView(this);
//            liftText.setText("Chest/Triceps Lift");
//            linearScroll.addView(liftText);
//            params = liftText.getLayoutParams();
//            CoachHomeScreen.setTextSettings(params, liftText);
//
//            dateText = new TextView(this);
//            linearScroll.addView(dateText);
//            params = dateText.getLayoutParams();
//            CoachHomeScreen.setTextSettings(params, dateText);
//            dateText.setText("10/21/21");
//
//            timeText = new TextView(this);
//            linearScroll.addView(timeText);
//            params = timeText.getLayoutParams();
//            CoachHomeScreen.setTextSettings(params, timeText);
//            timeText.setText("10:00 AM\n");
//        }
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