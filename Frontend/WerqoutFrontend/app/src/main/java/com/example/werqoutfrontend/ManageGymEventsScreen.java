package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ManageGymEventsScreen extends AppCompatActivity {

    /*
    all gym events listed. add and delete events.
     */

    /**
     * Overrides the onCreate function. Gives the interactive buttons and texts functionality.
     * Connects this class to manage_gym_events_screen.xml
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_gym_events_screen);
    }
}