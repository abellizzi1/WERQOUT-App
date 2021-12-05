package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;

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
     * Overrides the onCreate function. Gives the interactive buttons and texts functionality.
     * Connects this class to view_gym_team_screen.xml
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_gym_team_screen);
    }
}