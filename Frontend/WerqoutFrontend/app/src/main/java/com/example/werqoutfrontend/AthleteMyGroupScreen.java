package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.werqoutfrontend.model.Team;
import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AthleteMyGroupScreen extends AppCompatActivity {

    /**
     * The current rating of the athlete's team.
     */
    private double currentRating = 0;

    /**
     * The athlete's team id.
     */
    private int teamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.athlete_my_group_screen);

        Spinner userSpinner = (Spinner) findViewById(R.id.rating_spinner_athlete_mygroup);
        ArrayAdapter<String> userAdapter = new ArrayAdapter<String>(AthleteMyGroupScreen.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ratings));
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(userAdapter);

        TextView teamNameLabel = findViewById(R.id.group_name_label_athlete_mygroup);
        try {
            teamNameLabel.setText(AthleteHomeScreen.getAthleteTeam().get("name").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            teamNameLabel.setText("No team");
        }
        teamId = 0;
        try {
            teamId = (int)AthleteHomeScreen.getAthleteTeam().get("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Const.CURRENT_URL = "http://coms-309-034.cs.iastate.edu:8080/teams/" + teamId;
        ServerRequest getRating = new ServerRequest();
        getRating.jsonGetRequest(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    currentRating = (double)result.get("rating");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onSuccess(JSONArray result) {
            }
        }, Const.CURRENT_URL);

        TextView currentRatingText = findViewById(R.id.currTeamRating_label_athlete_mygroup);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentRatingText.setText("Current team rating: " + currentRating);
            }
        }, 200);

        Button backButton = findViewById(R.id.back_button_athlete_mygroup);
        backButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function directs the user to the AthleteHomeScreen Screen when the "Back" button
             * is clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), AthleteHomeScreen.class));
            }
        });

        Button submitRatingButton = findViewById(R.id.submit_button_athlete_mygroup);
        submitRatingButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function posts the selected rating to the team's rating.
             * @param view
             */
            @Override
            public void onClick(View view) {
                TextView errorMessage = findViewById(R.id.error_message_athlete_mygroup);
                errorMessage.setText("");
                String rating = userSpinner.getSelectedItem().toString();
                if (rating.equals("Select rating"))
                {
                    errorMessage.setText("Invalid rating");
                }
                else
                {
                    Const.CURRENT_URL = "http://coms-309-034.cs.iastate.edu:8080/teams/" + teamId + "/rate?rating=" + rating;
                    Map<String, String> params = new HashMap<>();
                    params.put("rating", rating);
                    ServerRequest ratingRequest = new ServerRequest();
                    ratingRequest.jsonObjectRequest(Const.CURRENT_URL, 2, new JSONObject(params));
                }
            }
        });

        Button leaveGroupButton = findViewById(R.id.leaveGroup_button_athlete_mygroup);
        leaveGroupButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function leaves the athlete's group.
             * @param view
             */
            @Override
            public void onClick(View view) {
                Const.CURRENT_URL = Const.URL_JSON_REQUEST_ATHLETES + "/" + LoginScreen.getId() + "/teams/" + teamId;
                ServerRequest removeAthleteRequest = new ServerRequest();
                removeAthleteRequest.jsonObjectRequest(Const.CURRENT_URL, 3, AthleteHomeScreen.getAthleteTeam());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(view.getContext(), AthleteHomeScreen.class));
                    }
                }, 100);
            }
        });
    }

    public int getRating(Team t)
    {
        //gets the rating for the team (used for testing)
        return 0;
    }
}