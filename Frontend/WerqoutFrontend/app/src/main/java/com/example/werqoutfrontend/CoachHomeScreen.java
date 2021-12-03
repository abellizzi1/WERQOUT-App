package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.VolleyCallback;
import com.example.werqoutfrontend.utils.VolleyCallbackImage;

/**
 * The CoachHomeScreen class gives functionality to the coach_home_screen.xml screen. It includes a weather
 * api that displays the weather in the user's area. This screen also displays the upcoming workouts for
 * the coach's teams in a Scroll View.
 * @author Angelo Bellizzi
 * @author Colin Brenizer
 */
public class CoachHomeScreen extends AppCompatActivity {
    private TextView temperatureView;
    private ImageView imageView;
    private String weatherIconCode = "";

    /**
     * The linear layout within the Scroll View in coach_home_screen.xml
     */
    private LinearLayout linearScroll;

    private static JSONObject coachTeam;
    private int teamId;

    private Context context = this;


    /**
     * Overrides the onCreate function. Gives the interactive buttons and texts functionality.
     * Connects this class to coach_home_screen.xml
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWeather();
        setContentView(R.layout.coach_home_screen);

        TextView welcomeLabel = findViewById(R.id.name_label_coach_home);
        welcomeLabel.setText("Hi, " + LoginScreen.getFirstName());

        /* Weather icon and temperature attributes */
        temperatureView = findViewById(R.id.weather_textview_athlete_home);
        imageView = findViewById(R.id.weather_icon_athlete_home);

        Button profileButton = findViewById(R.id.profile_button_coach_home);
        Button editGroupButton = findViewById(R.id.edit_button_coach_home);
        Button messageButton = findViewById(R.id.messages_button_athlete_home);
        Button searchButton = findViewById(R.id.search_button_athlete_home);

        editGroupButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function directs the user to the Coach Groups Screen when the
             * "edit" button is clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), CoachGroupsScreen.class));
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function directs the user to the Profile Screen when the "Profile" button is
             * clicked on the bottom taskbar.
             * @param view
             */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ProfileScreen.class));
            }
        });

        messageButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function directs the user to the Messages Screen when the "Messages" button
             * is clicked on the bottom taskbar.
             * @param view
             */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), SelectMessageScreen.class));
            }
        });

        /**
         * This onClick function directs the user to the Search Screen when the "Search" button
         * is clicked on the bottom taskbar.
         * @param view
         */
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), SearchScreen.class));
            }
        });

        linearScroll = (LinearLayout)findViewById(R.id.scrollLinear_coach_home);

        Const.CURRENT_URL = "http://coms-309-034.cs.iastate.edu:8080/coaches/" + LoginScreen.getId() + "/teams";
        ServerRequest getTeamId = new ServerRequest();
        getTeamId.jsonGetRequest(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    coachTeam = result;
                    teamId = ((int)coachTeam.get("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onSuccess(JSONArray result) {
            }
        }, Const.CURRENT_URL);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                displayTeamWorkouts();
            }
        }, 300);

    }

    private void displayTeamWorkouts()
    {
        Const.CURRENT_URL = "http://coms-309-034.cs.iastate.edu:8080/events/team/" + teamId + "/events";
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
                        TextView workoutText = new TextView(context);
                        String desc = workout.get("description").toString();
                        String dateAndTime = AddDeleteWorkoutScreen.formatDateTimeFromDatabase(workout.get("date").toString());
                        workoutText.setText(desc + "\n" + dateAndTime.substring(0, 8) + "\n" + dateAndTime.substring(9) + "\n");
                        linearScroll.addView(workoutText);
                        ViewGroup.LayoutParams params;
                        params = workoutText.getLayoutParams();
                        setTextSettings(params, workoutText);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, Const.CURRENT_URL);
    }

    private void getWeather()
    {
        ServerRequest weather = new ServerRequest();
        weather.jsonGetRequest(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                try {
                    //This reads the temperature from the response
                    JSONObject main = result.getJSONObject("main");
                    double temp = Double.parseDouble(main.getString("temp"));
                    int intTemp = (int)(((temp - 273.15) * (1.8)) + 32);
                    temperatureView.setText(intTemp + "ºF");
                    weatherIconCode += result.getJSONArray("weather").getJSONObject(0)
                            .getString("icon");

                    //This takes the icon code and gets the image through a image request
                    ServerRequest image = new ServerRequest();
                    String iconUrl = "http://openweathermap.org/img/w/" + weatherIconCode + ".png";
                    image.imageRequest(new VolleyCallbackImage() {
                        @Override
                        public void onSuccess(Bitmap response) {
                            imageView.setImageBitmap(response);
                        }
                    }, iconUrl);
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }
            }
            @Override
            public void onSuccess(JSONArray result) {
            }
        }, Const.WEATHER_API);
    }

    /**
     * Sets the text settings such as font, text color, and other params. This sets up
     * the text to be added to the Scroll View.
     * @param params
     * the params for the layout for the text
     * @param text
     * the text to be edited
     */
    public static void setTextSettings(ViewGroup.LayoutParams params, TextView text)
    {
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        text.setLayoutParams(params);
        text.setTextSize(25);
        text.setTextColor(Color.parseColor("#FFFFFF"));
    }
}