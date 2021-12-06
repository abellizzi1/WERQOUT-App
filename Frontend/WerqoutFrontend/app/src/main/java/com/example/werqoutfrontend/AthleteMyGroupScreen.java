package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.werqoutfrontend.model.Team;
import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AthleteMyGroupScreen extends AppCompatActivity {

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
        }

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
                    int teamId = 0;
                    try {
                        teamId = (int)AthleteHomeScreen.getAthleteTeam().get("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Const.CURRENT_URL = "http://coms-309-034.cs.iastate.edu:8080/teams/" + teamId + "/rate?rating=" + rating;
                    Map<String, String> params = new HashMap<>();
                    params.put("rating", rating);
                    ServerRequest ratingRequest = new ServerRequest();
                    ratingRequest.jsonObjectRequest(Const.CURRENT_URL, 2, new JSONObject(params));
                }
            }
        });
    }

    public int getRating(Team t)
    {
        //gets the rating for the team (used for testing)
        return 0;
    }
}