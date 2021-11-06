package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
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

public class AthleteHomeScreen extends AppCompatActivity {

    private TextView temperatureView;
    private ImageView imageView;
    private String weatherIconCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWeather();
        setContentView(R.layout.athlete_home_screen);

        TextView welcomeLabel = findViewById(R.id.name_label_athlete_home);
        welcomeLabel.setText("Hi, " + LoginScreen.getFirstName());

        /* Weather icon and temperature attributes */
        temperatureView = findViewById(R.id.weather_textview_athlete_home);
        imageView = findViewById(R.id.weather_icon_athlete_home);

        Button profileButton = findViewById(R.id.profile_button_athlete_home);
        Button messageButton = findViewById(R.id.messages_button_athlete_home);
        Button searchButton = findViewById(R.id.search_button_athlete_home);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ProfileScreen.class));
            }
        });

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), SearchScreen.class));
            }
        });

        LinearLayout linearScroll = (LinearLayout)findViewById(R.id.scrollLinear_athlete_home);
        ViewGroup.LayoutParams params;
        TextView liftText;
        TextView dateText;
        TextView timeText;
        for (int i = 0; i < 10; i++)
        {
            liftText = new TextView(this);
            liftText.setText("Chest/Triceps Lift");
            linearScroll.addView(liftText);
            params = liftText.getLayoutParams();
            CoachHomeScreen.setTextSettings(params, liftText);

            dateText = new TextView(this);
            linearScroll.addView(dateText);
            params = dateText.getLayoutParams();
            CoachHomeScreen.setTextSettings(params, dateText);
            dateText.setText("10/21/21");

            timeText = new TextView(this);
            linearScroll.addView(timeText);
            params = timeText.getLayoutParams();
            CoachHomeScreen.setTextSettings(params, timeText);
            timeText.setText("10:00 AM\n");
        }


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
        },Const.WEATHER_API);
    }
}