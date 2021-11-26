package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;

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
    }
}