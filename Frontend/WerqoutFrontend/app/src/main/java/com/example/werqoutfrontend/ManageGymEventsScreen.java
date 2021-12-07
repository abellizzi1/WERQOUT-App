package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * The ManageGymEventsScreen class gives functionality to the maange_gym_events_screen.xml screen. It gives
 * the gym owner the ability to create an event at their gym or delete the current event at their gym.
 * @author Angelo Bellizzi
 */
public class ManageGymEventsScreen extends AppCompatActivity {

    /**
     * The name of the event to be added.
     */
    private String eventName;

    /**
     * The date of the event to be added.
     */
    private String eventDate;

    /**
     * The time of the event to be added.
     */
    private String eventTime;

    /**
     * The current event at the gym.
     */
    private JSONObject currentEvent;

    /**
     * Overrides the onCreate function. Gives the interactive buttons and texts functionality.
     * Connects this class to manage_gym_events_screen.xml
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_gym_events_screen);

        Spinner userSpinner = (Spinner) findViewById(R.id.ampm_spinner_manageGymEvents);
        ArrayAdapter<String> userAdapter = new ArrayAdapter<String>(ManageGymEventsScreen.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.time));
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(userAdapter);

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
                    currentEvent = result;
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

        Button deleteEventButton = findViewById(R.id.delete_button_manageGymEvents);
        deleteEventButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function deletes the gym's current event.
             * @param view
             */
            @Override
            public void onClick(View view) {
                try {
                    Const.CURRENT_URL = "http://coms-309-034.cs.iastate.edu:8080/events/gym/" + LoginScreen.getId() + "/" + ((int)currentEvent.get("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ServerRequest removeEventRequest = new ServerRequest();
                removeEventRequest.jsonObjectRequest(Const.CURRENT_URL, 3, currentEvent);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(view.getContext(), ManageGymEventsScreen.class));
                    }
                }, 100);
            }
        });

        EditText nameInput = findViewById(R.id.name_input_manageGymEvents);
        EditText dateInput = findViewById(R.id.date_input_manageGymEvents);
        EditText timeInput = findViewById(R.id.time_input_manageGymEvents);
        Button addButton = findViewById(R.id.add_button_manageGymEvents);
        addButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function adds an event to a gym when the "add" button is clicked.
             * If the information for the new event is invalid, an error message appears when the "add"
             * button is clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {
                String errorMsg = "";
                TextView errorMessage = findViewById(R.id.errorMessage_label_manageGymEvents);
                errorMessage.setText("");
                eventName = nameInput.getText().toString();
                eventDate = dateInput.getText().toString();
                eventTime = timeInput.getText().toString();

                if (eventName.length() == 0)
                {
                    errorMsg += "Workout name ";
                }
                if (!AddDeleteWorkoutScreen.isValidDate(eventDate))
                {
                    errorMsg += "Date ";
                }
                if (!AddDeleteWorkoutScreen.isValidTime(eventTime))
                {
                    errorMsg += "Time ";
                }
                if (userSpinner.getSelectedItem().toString().equals("Select AM or PM"))
                {
                    errorMsg += "Dropdown ";
                }
                if (errorMsg.length() > 0)
                {
                    errorMsg += "invalid";
                    errorMessage.setText(errorMsg);
                }
                else
                {
                    //create new event and add it to the database
                    Map<String, String> params = new HashMap<>();
                    params.put("description", eventName);
                    params.put("date", AddDeleteWorkoutScreen.formatDateTimeToDatabase(eventDate, eventTime, userSpinner.getSelectedItem().toString().equals("AM")));
                    ServerRequest request = new ServerRequest();
                    Const.CURRENT_URL = "http://coms-309-034.cs.iastate.edu:8080/events/gym/" + LoginScreen.getId() + "/event";
                    request.jsonObjectRequest(Const.CURRENT_URL,1, new JSONObject(params));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(view.getContext(), ManageGymEventsScreen.class));
                        }
                    }, 100);
                }
            }
        });

    }
}