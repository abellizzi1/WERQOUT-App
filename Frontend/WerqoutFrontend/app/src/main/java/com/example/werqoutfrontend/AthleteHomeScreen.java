package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.werqoutfrontend.model.Athlete;

public class AthleteHomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.athlete_home_screen);

        TextView welcomeLabel = findViewById(R.id.name_label_athlete_home);
        // set welcomeLabel text to "Hi, [firstName]!"
        // ^ get firstName ?
    }
}