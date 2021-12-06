package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.VolleyCallback;
import com.example.werqoutfrontend.utils.VolleyCallbackImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class GymOwnerHomeScreen extends AppCompatActivity {

    private TextView temperatureView;
    private ImageView imageView;
    private String weatherIconCode = "";

    /**
     * Overrides the onCreate function. Gives the interactive buttons and texts functionality.
     * Connects this class to gym_owner_home_screen.xml
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWeather();
        setContentView(R.layout.gym_owner_home_screen);

        TextView welcomeLabel = findViewById(R.id.name_label_gymOwner_home);
        TextView gymNameLabel = findViewById(R.id.actualGymName_label_gymOwner_home);
        welcomeLabel.setText("Hi, " + LoginScreen.getFirstName());
        gymNameLabel.setText(LoginScreen.getGymId());

        /* Weather icon and temperature attributes */
        temperatureView = findViewById(R.id.weather_textview_athlete_home);
        imageView = findViewById(R.id.weather_icon_athlete_home);

        Button profileButton = findViewById(R.id.profile_button_gymOwner_home);
        Button messageButton = findViewById(R.id.messages_button_gymOwner_home);
        Button searchButton = findViewById(R.id.search_button_gymOwner_home);
        Button manageTeamsButton = findViewById(R.id.manageTeams_button_gymOwner_home);
        Button manageEventsButton = findViewById(R.id.manageEvents_button_gymOwner_home);

        profileButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function directs the user to the Profile Screen when the "Profile" button
             * is clicked on the bottom taskbar.
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

        searchButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function directs the user to the Search Screen when the "Search" button
             * is clicked on the bottom taskbar.
             * @param view
             */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), SearchScreen.class));
            }
        });

        manageTeamsButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function directs the user to the Manage Teams Screen when the
             * "Manage Teams" button is clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ManageGymTeamsScreen.class));
            }
        });

        manageEventsButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick function directs the user to the Manage Events Screen when the
             * "Manage Events" button is clicked.
             * @param view
             */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ManageGymEventsScreen.class));
            }
        });


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
}