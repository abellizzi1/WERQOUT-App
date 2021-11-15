package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.werqoutfrontend.model.Athlete;
import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The AddDeleteWorkoutScreen class gives functionality to the add_delete_workout_screen.xml screen.
 * It includes methods that verify if the created workout is valid, and also gives the user the option
 * to delete a selected workout.
 * @author Angelo Bellizzi
 */
public class AddDeleteWorkoutScreen extends AppCompatActivity implements View.OnClickListener {

    /**
     * The name of the workout to be added.
     */
    private String workoutName;

    /**
     * The date of the workout to be added.
     */
    private String workoutDate;

    /**
     * The time of the workout to be added.
     */
    private String workoutTime;

    /**
     * The linear layout within the Scroll View in add_delete_workout_screen.xml
     */
    private LinearLayout linearScroll;

    private ArrayList<JSONObject> jsonWorkoutsArray;

    private JSONObject selectedWorkoutJson;

    private static String selectedWorkoutString = "";

    /**
     * Overrides the onCreate function. Gives the interactive buttons and texts functionality.
     * Connects this class to add_delete_workout_screen.xml
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_delete_workout_screen);

        Spinner userSpinner = (Spinner) findViewById(R.id.ampm_spinner_add_delete);
        linearScroll = (LinearLayout)findViewById(R.id.scrollLinear_add_delete);
        jsonWorkoutsArray = new ArrayList<JSONObject>();
        Context context = this;

        ArrayAdapter<String> userAdapter = new ArrayAdapter<String>(AddDeleteWorkoutScreen.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.time));
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(userAdapter);

        Button backButton = findViewById(R.id.back_button_add_delete);
        backButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function directs the user to the Group Info Screen when the "back" button is clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), GroupInfoScreen.class));
            }
        });

        EditText workoutNameInput = findViewById(R.id.workoutName_input_add_delete);
        EditText dateInput = findViewById(R.id.date_input_add_delete);
        EditText timeInput = findViewById(R.id.time_input_add_delete);
        Button addButton = findViewById(R.id.add_button_add_delete);

        addButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function adds a workout to a Team when the "add" button is clicked.
             * If the information for the new workout is invalid, an error message appears when the "add"
             * button is clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {
                String errorMsg = "";
                TextView errorMessage = findViewById(R.id.errorMessage_label_add_delete);
                errorMessage.setText("");
                workoutName = workoutNameInput.getText().toString();
                workoutDate = dateInput.getText().toString();
                workoutTime = timeInput.getText().toString();

                if (workoutName.length() == 0)
                {
                    errorMsg += "Workout name ";
                }
                if (!isValidDate(workoutDate))
                {
                    errorMsg += "Date ";
                }
                if (!isValidTime(workoutTime))
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
                    //create new workout and add it to the database
                    Map<String, String> params = new HashMap<>();
                    params.put("description", workoutName);
                    params.put("date", formatDateTimeToDatabase(workoutDate, workoutTime, userSpinner.getSelectedItem().toString().equals("AM")));
                    ServerRequest request = new ServerRequest();
                    Const.CURRENT_URL = "http://coms-309-034.cs.iastate.edu:8080/events/create";
                    request.jsonObjectRequest(Const.CURRENT_URL,1, new JSONObject(params));
                    startActivity(new Intent(view.getContext(), AddDeleteWorkoutScreen.class));
                }
            }
        });

        Const.CURRENT_URL = Const.URL_JSON_REQUEST_EVENTS;
        ServerRequest allWorkouts = new ServerRequest();
        allWorkouts.jsonArrayRequest(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
            }

            @Override
            public void onSuccess(JSONArray users) {
                for(int i = 0; i < users.length(); i++)
                {
                    try {
                        JSONObject workout = users.getJSONObject(i);
                        jsonWorkoutsArray.add(workout);
                        Button workoutButton = new Button (context);
                        String desc = workout.get("description").toString();
                        String dateAndTime = formatDateTimeFromDatabase(workout.get("date").toString());
                        workoutButton.setText(desc + " " + dateAndTime);
                        linearScroll.addView(workoutButton);
                        ViewGroup.LayoutParams params;
                        params = workoutButton.getLayoutParams();
                        params.height = 200;
                        params.width = 1409;
                        workoutButton.setLayoutParams(params);
                        workoutButton.setTextSize(25);
                        workoutButton.setTextColor(Color.parseColor("#000000"));
                        workoutButton.setBackgroundColor(Color.parseColor("#00FFA7"));
                        workoutButton.setId(i);
                        workoutButton.setOnClickListener((View.OnClickListener) context);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, Const.CURRENT_URL);
    }

    /**
     * Checks if the parameter is a valid date for the workout. (must be in the format: mm/dd/yy)
     * @param date
     * the inputted date that needs verification
     * @return
     * if the parameter is a valid date, it will return true, else false.
     */
    public boolean isValidDate(String date)
    {
        String[] dateSplit = date.split("/");
        if (dateSplit.length == 3 && TextUtils.isDigitsOnly(dateSplit[0] + dateSplit[1] + dateSplit[2])
            && dateSplit[0].length() == 2 && dateSplit[1].length() == 2 && dateSplit[2].length() == 2)
        {
            int month = Integer.parseInt(dateSplit[0]);
            int day = Integer.parseInt(dateSplit[1]);
            int year = Integer.parseInt(dateSplit[2]);
            return (month >= 1 && month <= 12 && day >= 1 && day <= 31 && year >= 21);
        }
        return false;
    }

    /**
     * Checks if the parameter is a valid time for the workout. (must be in the format: 00:00)
     * @param time
     * the inputted time that needs verification
     * @return
     * if the parameter is a valid time, it will return true, else false.
     */
    public boolean isValidTime(String time)
    {
        String[] timeSplit = time.split(":");
        if (timeSplit.length == 2 && TextUtils.isDigitsOnly(timeSplit[0] + timeSplit[1])
            && timeSplit[0].length() == 2 && timeSplit[1].length() == 2)
        {
            int hour = Integer.parseInt(timeSplit[0]);
            int minute = Integer.parseInt(timeSplit[1]);
            return (hour >= 0 && hour <= 12 && minute >= 0 && minute <= 59);
        }
        return false;
    }

    /**
     * This onClick function keeps track of the selected workout. Once a workout is selected,
     * it can be deleted from the team.
     * @param v
     */
    @Override
    public void onClick(View v) {
        selectedWorkoutString = ((Button)linearScroll.findViewById(v.getId())).getText().toString();
        TextView selected = findViewById(R.id.selected_label_add_delete);
        selected.setText("Selected: " + selectedWorkoutString);
        selectedWorkoutJson = jsonWorkoutsArray.get(v.getId());
    }

    /**
     * Converts the date and time from the database into the correct format.
     * (No military time, use of AM/PM, date in mm/dd/yy)
     * @param dt
     * the date/time to be re-formatted
     * @return
     * returns the date/time in the correct format
     */
    public String formatDateTimeFromDatabase(String dt)
    {
        // 2021-01-01T13:30:00.000+00:00 (date: yyyy-mm-dd)
        String year = dt.substring(2, 4);
        String month = dt.substring(5, 7);
        String day = dt.substring(8, 10);
        String hour = dt.substring(11, 13);
        String minute = dt.substring(14, 16);
        String ampm = "AM";
        int hourInt = Integer.parseInt(hour);
        if (hourInt >= 13)
        {
            ampm = "PM";
        }
        if (hour.equals("00"))
        {
            hour = "12";
        }
        return month + "/" + day + "/" + year + " " + hour + ":" + minute + " " + ampm;
    }

    /**
     * Converts the date and time so that it can be posted to the database correctly.
     * @param date
     * the date of the workout
     * @param time
     * the time of the workout
     * @param isAM
     * true if the time is AM, else false
     * @return
     * returns a string that is able to be posted to the events database.
     */
    public String formatDateTimeToDatabase(String date, String time, boolean isAM)
    {
        /*
        2021-01-01T13:30:00.000+00:00 (date: yyyy-mm-dd)
        date: mm/dd/yy
        time: 00:00
         */
        String month = date.substring(0, 2);
        String day = date.substring(3, 5);
        String year = "20" + date.substring(6);
        int hour = Integer.parseInt(time.substring(0, 2));
        if (!isAM && hour != 12)
        {
            hour += 12;
        }
        String t = "T" + hour + time.substring(2);
        return year + "-" + month + "-" + day + t;
    }
}