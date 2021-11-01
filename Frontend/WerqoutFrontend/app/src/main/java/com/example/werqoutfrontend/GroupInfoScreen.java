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

public class GroupInfoScreen extends AppCompatActivity {

    private String TAG = ServerRequest.class.getSimpleName();
    private String tag_json_obj_post = "jobj_req_post";
    public Team selectedGroup;

    /*
    public void getJsonLoginInfo()
    {
        if (userSpinner.getSelectedItem().toString().equals("Athlete"))
        {
            Const.CURRENT_URL = Const.URL_JSON_REQUEST_ATHLETES;
        }
        else if (userSpinner.getSelectedItem().toString().equals("Coach"))
        {
            Const.CURRENT_URL = Const.URL_JSON_REQUEST_COACHES;
        }
        else
        {
            Const.CURRENT_URL = Const.URL_JSON_REQUEST_GYMOWNER;
        }

        ServerRequest userLogin = new ServerRequest();
        userLogin.jsonArrayRequest(new VolleyCallback() {
            //Is there a way that I don't need to include this method?
            @Override
            public void onSuccess(JSONObject result) {
            }

            @Override
            public void onSuccess(JSONArray users) {
                for(int i = 0; i < users.length(); i++)
                {
                    try {
                        JSONObject user = users.getJSONObject(i);
                        if(user.get("email").toString().equals(email))
                        {

                            emailResponse = user.get("email").toString();
                            passwordResponse = user.get("password").toString();
                            firstName = user.get("userName").toString();
                            id = Integer.valueOf(user.get("id").toString());
                            Athlete athlete = new Athlete(emailResponse,passwordResponse,firstName,id);
                            break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                login();
            }
        },Const.CURRENT_URL);
    }
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_info_screen);

        TextView groupNameLabel = findViewById(R.id.group_name_label_group_info);
        Button backButton = findViewById(R.id.back_button_group_info);
        Button addDeleteButton = findViewById(R.id.addDeleteWorkouts_button_group_info);
        groupNameLabel.setText(CoachGroupsScreen.getSelectedGroup());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), CoachGroupsScreen.class));
            }
        });
        addDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), AddDeleteWorkoutScreen.class));
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

        Const.CURRENT_URL = "http://coms-309-034.cs.iastate.edu:8080/teams/1/athletes";
        ServerRequest displayMembers = new ServerRequest();
        displayMembers.jsonArrayRequest(new VolleyCallback() {
            //Is there a way that I don't need to include this method?
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
    }
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
//    }

    public ArrayList<Workout> getWorkouts(Team t)
    {
        //gets workouts from database
        return null;
    }

    public ArrayList<Athlete> getAthletes(Team t)
    {
        //gets athletes from database
        return null;
    }
}