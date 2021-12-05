package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        Button backButton = findViewById(R.id.back_button_manageGymEvents);
        backButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function directs the user to the Gym Owner Home Screen when the "back"
             * button is clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), GymOwnerHomeScreen.class));
            }
        });

        TextView currentGymEvent = findViewById(R.id.currentGymEvent_label_manageGymEvents);
        Const.CURRENT_URL = "http://coms-309-034.cs.iastate.edu:8080/events/gym/" + LoginScreen.getId() + "/events";
        ServerRequest getGymEvent = new ServerRequest();
        getGymEvent.jsonGetRequest(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    String desc = result.get("description").toString();
                    String dateAndTime = AddDeleteWorkoutScreen.formatDateTimeFromDatabase(result.get("date").toString());
                    currentGymEvent.setText(desc + "\n" + dateAndTime);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onSuccess(JSONArray result) {
            }
        }, Const.CURRENT_URL);
    }
}